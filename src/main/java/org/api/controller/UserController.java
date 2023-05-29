package org.api.controller;

import org.api.annotation.LogExecutionTime;
import org.api.payload.ResultBean;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
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
@RequestMapping(value = "/v1/api/users/")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserEntityService userEntityService;

    @PostMapping(value = "/create-user", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createUser(@RequestBody String json){
        try{
            ResultBean resultBean = userEntityService.createUser(json);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        }catch (ApiValidateException ex){
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getUserById(@PathVariable String id) {
        try{
            ResultBean resultBean = userEntityService.getById(id);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        }catch (ApiValidateException ex){
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_OK,ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

//    @GetMapping(value = "/get-user", produces = { MediaType.APPLICATION_JSON_VALUE })
//    public ResponseEntity<ResultBean> getUser(
//            @RequestParam(value = "sort", defaultValue = "asc", required = false) String sort,
//            @RequestParam(value = "column", defaultValue = "id", required = false) String column,
//            @RequestParam(value = "keyWord", defaultValue = "", required = false) String keyWord,
//            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
//            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) throws Exception {
//        ResultBean resultBean = userEntityService.getUsers(sort,column, keyWord, page, size);
//        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.OK);
//    }

//    @PostMapping(value = "/upload-avatar-image/{id}", produces = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
//    public ResponseEntity<ResultBean> updateAvatar(@PathVariable String id, @RequestPart("file") MultipartFile file) throws Exception, ApiValidateException {
//        ResultBean resultBean = userEntityService.updateAvatar(file, id);
//        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
//    }
//
//    @PostMapping(value = "/upload-banner-image/{id}", produces = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
//    public ResponseEntity<ResultBean> updateBanner(@PathVariable String id, @RequestPart("file") MultipartFile file) throws Exception, ApiValidateException {
//        ResultBean resultBean = userEntityService.updateBanner(file, id);
//        return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
//    }



}
