package org.api.controller;

import org.api.annotation.LogExecutionTime;
import org.api.payload.ResultBean;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.services.PostEntityService;
import org.api.services.RelationshipEntityService;
import org.api.utils.ApiValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

@LogExecutionTime
@RestController
@RequestMapping(value = "/v1/api/posts/")
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostEntityService postEntityService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RelationshipEntityService relationshipEntityService;

    @PostMapping(value = "/create-post",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createPost(@RequestPart("json") String json, @RequestPart("files") MultipartFile[] files) {
        try{
            ResultBean resultBean = postEntityService.createPost(json, files);

            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        }catch (ApiValidateException ex){
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/get-all-by-properties-where-id-user", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getAllByPropertiesWhereIdUser(@RequestParam(name = "accessModifierLevel", defaultValue = "Public", required = false) String accessModifierLevel,
                                                                    @RequestParam(name = "typePost", defaultValue = "Myself", required = false) String typePost,
                                                                    @RequestParam(name = "idUser", required = false) String idUser,
                                                                    @RequestParam(name = "typeAlbum", required = false) String typeAlbum,
                                                                    @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") Date startDate,
                                                                    @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") Date endDate) {
        try{
            ResultBean resultBean = postEntityService.getAllByPropertiesWhereIdUser(accessModifierLevel, typePost, idUser, typeAlbum, startDate, endDate);
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        }catch (ApiValidateException ex){
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_OK,ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

}
