package com.gamesys.api.register.domain;

import com.gamesys.api.register.repository.RegisterRepository;
import com.gamesys.api.register.repository.model.Register;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RegisterServiceIT {
    private static final String USER_NAME_1 = "USER_NAME_1";
    private static final String PASSWORD_1 = "PASSWORD_1";
    private static final String DOB_1 = "DOB_1";
    private static final String CARD_NUMBER_1 = "CARD_NUMBER_1";
    private static final String USER_NAME_2 = "USER_NAME_2";
    private static final String PASSWORD_2 = "PASSWORD_2";
    private static final String DOB_2 = "DOB_2";
    private static final String CARD_NUMBER_2 = "CARD_NUMBER_2";
    private static final String USER_NAME_3 = "USER_NAME_3";
    private static final String PASSWORD_3 = "PASSWORD_3";
    private static final String DOB_3 = "DOB_3";
    private static final String CARD_NUMBER_3 = "CARD_NUMBER_3";
    private static final Register REGISTER_1 = new Register(USER_NAME_1, PASSWORD_1, DOB_1, CARD_NUMBER_1);
    private static final Register REGISTER_2 = new Register(USER_NAME_2, PASSWORD_2, DOB_2, CARD_NUMBER_2);
    private static final Register REGISTER_3 = new Register(USER_NAME_3, PASSWORD_3, DOB_3, CARD_NUMBER_3);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RegisterRepository repository;

    @Test
    public void findByUserName() {

        entityManager.persist(REGISTER_1);

        List<Register> registerList = repository.findByUserName(USER_NAME_1);
        assertThat(registerList)
                .isNotEmpty()
                .extracting(Register::getUserName,
                        Register::getPassword,
                        Register::getDob,
                        Register::getPaymentCardNumber)
                .containsExactly(new Tuple(USER_NAME_1, PASSWORD_1, DOB_1, CARD_NUMBER_1));
    }

    @Test
    public void findByUserName_withUnSavedUserName() {

        entityManager.persist(REGISTER_2);

        List<Register> registerList = repository.findByUserName("unknown");

        assertThat(registerList)
                .isEmpty();
    }

    @Test
    public void save() {

        Register registerSaved = repository.save(REGISTER_3);

        Optional<Register> registerOp = repository.findById(registerSaved.getId());

        assertThat(registerOp)
                .isNotEmpty()
                .containsSame(registerSaved);
    }
}