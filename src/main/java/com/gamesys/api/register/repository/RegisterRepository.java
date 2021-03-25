package com.gamesys.api.register.repository;

import com.gamesys.api.register.repository.model.Register;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepository extends CrudRepository<Register, Long> {

    List<Register> findByUserName(String userName);
}
