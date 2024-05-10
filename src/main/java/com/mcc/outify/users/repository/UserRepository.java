package com.mcc.outify.users.repository;

import com.mcc.outify.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByAccount(String account);

    boolean existsByAccount(String account);

}
