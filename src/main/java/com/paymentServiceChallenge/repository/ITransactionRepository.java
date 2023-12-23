package com.paymentServiceChallenge.repository;

import com.paymentServiceChallenge.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transaction,Long> {
}
