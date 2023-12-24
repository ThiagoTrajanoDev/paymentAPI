package com.paymentServiceChallenge.service;

import com.paymentServiceChallenge.DTOs.NotificationDTO;
import com.paymentServiceChallenge.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificationService {


    private final RestClient restClient;

    public NotificationService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
                .build();
    }

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();

        NotificationDTO NotificationRequest = new NotificationDTO(email,message);
        ResponseEntity<String>  notificationResponse = restClient.post().body(NotificationRequest).retrieve().toEntity(String.class);

        if (!(notificationResponse.getStatusCode() == HttpStatus.OK)){
            System.out.println("Erro ao enviar notificação");
            throw  new Exception("Serviço de notificação está fora do ar");

        }
    }
}
