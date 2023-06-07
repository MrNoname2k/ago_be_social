package org.api.controller;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.payload.ResultBean;
import org.api.services.NotificationEntityService;
import org.api.utils.ApiValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@LogExecutionTime
@RestController
@RequestMapping("/v1/api/notification")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationEntityService notificationEntityService;

//    @PostMapping("/push")
//    public ResponseEntity<WebNotification> sendWebNotification(@RequestBody WebNotification request) {
//        try {
//            WebpushConfig.Builder builder = WebpushConfig.builder()
//                    .setNotification(new WebpushNotification(request.getTitle(), request.getBody()));
//            FirebaseMessaging.getInstance().send(Message.builder()
//                    .setToken(request.getToken())
//                    .setWebpushConfig(builder.build())
//                    .build());
//
//            return new ResponseEntity<>(request, HttpStatus.OK);
//        } catch (FirebaseMessagingException e) {
//            System.out.println(e);
//            return new ResponseEntity<>(request, HttpStatus.EXPECTATION_FAILED);
//        }
//    }

    @GetMapping(value = "/get-all-notification-by-id-user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResultBean> getAllNotificationByIdUser(@RequestParam(name = "size", required = false) Integer size,
                                                                 @RequestParam(name = "idUser", required = false) String idUser) {
        try {
            ResultBean resultBean = notificationEntityService.findAllByPostEntityUserEntityPostId(size, idUser);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }
}

