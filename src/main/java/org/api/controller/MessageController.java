package org.api.controller;

import org.api.annotation.LogExecutionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@LogExecutionTime
@RestController
@RequestMapping(value = "/v1/api/messages/")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);


}
