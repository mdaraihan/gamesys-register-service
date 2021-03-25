package com.gamesys.api.register.domain;

import com.gamesys.api.register.domain.validator.CreateRegisterValidator;
import com.gamesys.api.register.domain.validator.RegisterValidationError;
import com.gamesys.api.register.domain.validator.RegisterValidationResult;
import com.gamesys.api.register.repository.RegisterRepository;
import io.vavr.control.Either;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RegisterService {

    private final RegisterRepository registerRepository;
    private final DomainRegisterFactory domainRegisterFactory;
    private final CreateRegisterValidator createRegisterValidator;

    public RegisterService(RegisterRepository registerRepository,
                           DomainRegisterFactory domainRegisterFactory,
                           CreateRegisterValidator createRegisterValidator) {
        this.registerRepository = registerRepository;
        this.domainRegisterFactory = domainRegisterFactory;
        this.createRegisterValidator = createRegisterValidator;
    }

    public Either<Set<RegisterValidationError>, DomainRegister> create(DomainRegister domainRegister) throws Exception {

        RegisterValidationResult registerValidationResult = createRegisterValidator.validate(domainRegister);


        if (!registerValidationResult.isSuccessful()) {
            return Either.left(registerValidationResult.getErrors());
        } else {
            return Either.right(Stream.of(domainRegisterFactory.createRegister(domainRegister))
                    .map(registerRepository::save)
                    .map(domainRegisterFactory::createDomainRegister)
                    .findAny()
                    .orElseThrow(Exception::new));
        }
    }

    public List<DomainRegister> findAll() {
        return StreamSupport.stream(registerRepository.findAll().spliterator(), false)
                .map(domainRegisterFactory::createDomainRegister)
                .collect(Collectors.toList());
    }

    public Optional<DomainRegister> findById(Long id) {
        return registerRepository.findById(id)
                .map(domainRegisterFactory::createDomainRegister);
    }
}
