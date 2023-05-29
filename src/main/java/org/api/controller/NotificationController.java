package org.api.controller;

import com.google.firebase.messaging.*;
import org.api.annotation.LogExecutionTime;
import org.api.payload.WebNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@LogExecutionTime
@RestController
@RequestMapping("/v1/api/notification")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping("/push")
    public ResponseEntity<WebNotification> sendWebNotification(@RequestBody WebNotification request) {
        try {
            WebpushConfig.Builder builder = WebpushConfig.builder()
                    .setNotification(new WebpushNotification(request.getTitle(), request.getBody()));
            FirebaseMessaging.getInstance().send(Message.builder()
                    .setToken(request.getToken())
                    .setWebpushConfig(builder.build())
                    .build());

            return new ResponseEntity<>(request,HttpStatus.OK);
        } catch (FirebaseMessagingException e) {
            System.out.println(e);
            return new ResponseEntity<>(request,HttpStatus.EXPECTATION_FAILED);
        }
    }
}

