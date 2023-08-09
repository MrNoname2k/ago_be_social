package org.api.controller.admin;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.controller.AgoHomeController;
import org.api.payload.ResultBean;
import org.api.services.PostEntityService;
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
@RequestMapping(value = "/v1/api/ago/admin/post")
public class AdminPostManagerController {
    private static final Logger log = LoggerFactory.getLogger(AgoHomeController.class);
    @Autowired
    private PostEntityService postEntityService;

    @GetMapping("/post-legal")
    public ResponseEntity<ResultBean> getLegalPosts(){
        try{
            ResultBean resultBean = postEntityService.getLegalPosts();
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }
    @GetMapping("/post-illegal")
    public ResponseEntity<ResultBean> getillegalPosts(){
        try{
            ResultBean resultBean = postEntityService.getIllegalPosts();
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @GetMapping("/post-deleted")
    public ResponseEntity<ResultBean> getAllPostDelete(){
        try{
            ResultBean resultBean = postEntityService.getAllPostsSoftDelete();
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
    public ResponseEntity<ResultBean> getPostById(@PathVariable String id){
        try{
            ResultBean resultBean = postEntityService.getbyId(id);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/soft-delete",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResultBean> softDeletePost(@RequestBody String json) {
        try {
            ResultBean resultBean = postEntityService.softDeletePostById(json);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/recover-post",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResultBean> recoverUser(@RequestBody String json) {
        try {
            ResultBean resultBean = postEntityService.recoverPostSoftDelete(json);
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
