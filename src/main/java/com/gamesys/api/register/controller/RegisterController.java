package com.gamesys.api.register.controller;

import com.gamesys.api.register.domain.RegisterService;
import com.gamesys.api.register.domain.DomainRegister;
import com.gamesys.api.register.domain.validator.RegisterValidationError;
import com.gamesys.api.register.exception.AgeValidationException;
import com.gamesys.api.register.exception.BasicValidationException;
import com.gamesys.api.register.exception.PaymentCardBlockedException;
import com.gamesys.api.register.exception.ResourceNotFoundException;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.AGE_UNDER_18;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.PAYMENT_CARD_ISSUER_BLOCKED;
import static java.util.Collections.singleton;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class RegisterController {

    private final RegisterService registerService;
    private final RegisterDtoFactory registerDtoFactory;

    public RegisterController(RegisterService registerService,
                              RegisterDtoFactory registerDtoFactory) {
        this.registerService = registerService;
        this.registerDtoFactory = registerDtoFactory;
    }

    @GetMapping("/registers")
    @ResponseStatus(HttpStatus.OK)
    public List<RegisterResponse> getAllRegistered() {
        return registerService.findAll().stream()
                .map(registerDtoFactory::createRegisterResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/registers/{id}")
    public RegisterResponse getRegisterById(@PathVariable(value = "id") Long registerId) throws ResourceNotFoundException {
        return registerService.findById(registerId)
                .map(registerDtoFactory::createRegisterResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Register record not found for this id :: " + registerId));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse createRegister(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {

        DomainRegister domainRegister = registerDtoFactory.createDomainRegister(registerRequest);

        Either<Set<RegisterValidationError>, DomainRegister> result = registerService.create(domainRegister);

        return registerDtoFactory.createRegisterResponse(result.getOrElseThrow(this::throwException));
    }

    private Exception throwException(Set<RegisterValidationError> errors) {
        if (errors.contains(AGE_UNDER_18)) {
            return new AgeValidationException(singleton(AGE_UNDER_18).toString());
        } else if (errors.contains(PAYMENT_CARD_ISSUER_BLOCKED)) {
            return new PaymentCardBlockedException(singleton(PAYMENT_CARD_ISSUER_BLOCKED).toString());
        } else
            return new BasicValidationException(errors.toString());
    }
}
