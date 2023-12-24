package com.paymentServiceChallenge.service;

import com.paymentServiceChallenge.DTOs.UserDTO;
import com.paymentServiceChallenge.domain.user.User;
import com.paymentServiceChallenge.domain.user.UserType;
import com.paymentServiceChallenge.repository.IUserRepository;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário do tipo lojista não está autoriazado a realizar transação");
        }
        if(sender.getBalance().compareTo(amount)<0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return userRepository.findUserById(id).orElseThrow( ()-> new Exception("Usuário não encontrado"));
    }


    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public User createUser(UserDTO userData) {
        User newUser = new User(userData);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
