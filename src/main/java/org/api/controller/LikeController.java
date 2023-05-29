package org.api.controller;

import org.api.annotation.LogExecutionTime;
import org.api.payload.ResultBean;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.services.LikeEntityService;
import org.api.utils.ApiValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@LogExecutionTime
@RestController
@RequestMapping(value = "/v1/api/likes/")
public class LikeController {

    private static final Logger log = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    private LikeEntityService likeEntityService;

    @PostMapping(value = "/like-or-unlike", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultBean> createLike(@RequestBody String json) {
        try{
            ResultBean resultBean = likeEntityService.likeOrUnlike(json);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        }catch (ApiValidateException ex){
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }
}
