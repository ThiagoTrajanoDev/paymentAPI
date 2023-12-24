package com.paymentServiceChallenge.service;

import com.paymentServiceChallenge.DTOs.TransactionDTO;
import com.paymentServiceChallenge.domain.transaction.Transaction;
import com.paymentServiceChallenge.domain.user.User;
import com.paymentServiceChallenge.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    private final UserService userService;
    private final ITransactionRepository transactionRepository;

    private final NotificationService notificationService;
    private final RestClient restClient;

    public TransactionService   (UserService userService,
                                 ITransactionRepository transactionRepository, NotificationService notificationService) {
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        restClient = RestClient.builder()
                .baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
                .build();
    }

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender =  this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender,transaction.value());

        boolean isAuthorized =  this.authorizeTransaction(sender, transaction.value());
        if(!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction  = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        transactionRepository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        this.notificationService.sendNotification(sender,"Transação realizada com sucesso!");
        this.notificationService.sendNotification(receiver,"Transação recebida com sucesso!");
        return newTransaction;

    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationRepsonse  = restClient.get().retrieve().toEntity(Map.class);

        if(authorizationRepsonse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationRepsonse.getBody().get("message");
            return message.equals("Autorizado");
        }
        else return false;
    }
}
