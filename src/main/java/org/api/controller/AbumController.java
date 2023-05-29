package org.api.controller;

import org.api.annotation.LogExecutionTime;
import org.api.services.AbumEntityService;
import org.api.utils.ApiValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@LogExecutionTime
@RestController
@RequestMapping(value = "/v1/api/abums/")
public class AbumController {

    private static final Logger log = LoggerFactory.getLogger(AbumController.class);

    @Autowired
    private AbumEntityService abumEntityService;

    @PostMapping(value = "/create-abum-default", produces = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> createAbumDefault() throws Exception, ApiValidateException {
//        abumEntityService.createAbumDefault(ConstantTypeAbum.AVATAR);
//        abumEntityService.createAbumDefault(ConstantTypeAbum.BANNER);
        //abumEntityService.createAbumDefault(ConstantTypeAbum.POSTS);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
