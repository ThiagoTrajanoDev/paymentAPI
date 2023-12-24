package com.paymentServiceChallenge.DTOs;

import com.paymentServiceChallenge.domain.user.User;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {
}
