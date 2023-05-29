package org.api.controller;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantFirebase;
import org.api.services.FirebaseService;
import org.api.utils.ApiValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@LogExecutionTime
@RestController
@RequestMapping(value = "/v1/api/files/")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping(value = "/read-image/{idUser}/{fileName:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] readImage(@PathVariable String idUser, @PathVariable String fileName) throws Exception, ApiValidateException {
        return firebaseService.readImage(fileName, ConstantFirebase.FIREBASE_STORAGE_USER + idUser);
    }

}
