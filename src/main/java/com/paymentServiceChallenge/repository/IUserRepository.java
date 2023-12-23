package com.paymentServiceChallenge.repository;

import com.paymentServiceChallenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDocument(String document);
    Optional<User> findUserById(Long id);
}
