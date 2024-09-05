package org.bootcamp.bootcampapp.repository;

import org.bootcamp.bootcampapp.entity.AccountDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDataRepository extends JpaRepository<AccountDataEntity, Integer> {

    Optional<AccountDataEntity> findByLoginAndPassword(String login, String password);

    AccountDataEntity save(AccountDataEntity accountDataEntity);

    Optional<AccountDataEntity> findById(int id);

}
