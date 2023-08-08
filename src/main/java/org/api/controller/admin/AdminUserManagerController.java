package org.api.controller.admin;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.controller.AgoHomeController;
import org.api.entities.UserEntity;
import org.api.payload.ResultBean;
import org.api.services.UserEntityService;
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
@RequestMapping(value = "/v1/api/ago/admin")
public class AdminUserManagerController {
    private static final Logger log = LoggerFactory.getLogger(AgoHomeController.class);
    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("/user-manager")
    public ResponseEntity<ResultBean> getAllUser(){
        try{
            ResultBean resultBean = userEntityService.getAll();
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<ResultBean> getUserById(@PathVariable String id){
        try{
            ResultBean resultBean = userEntityService.getById(id);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/update-user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResultBean> updateUser(@RequestBody String json) {
        try {
            ResultBean resultBean = userEntityService.updateUser(json);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }
    @PutMapping(value = "/soft-delete",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResultBean> softDeleteUser(@RequestBody String json) {
        System.out.println(json + "ASO");
        try {
            ResultBean resultBean = userEntityService.softDeleteUserById(json);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

}
