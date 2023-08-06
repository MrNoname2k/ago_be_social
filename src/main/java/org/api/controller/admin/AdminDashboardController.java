package org.api.controller.admin;

import org.api.annotation.LogExecutionTime;
import org.api.constants.ConstantMessage;
import org.api.constants.ConstantStatus;
import org.api.controller.AgoHomeController;
import org.api.payload.ResultBean;
import org.api.services.UserEntityService;
import org.api.services.admin.DashBoarService;
import org.api.utils.ApiValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@LogExecutionTime
@RestController
@RequestMapping(value = "/v1/api/ago/admin")
public class AdminDashboardController {
    private static final Logger log = LoggerFactory.getLogger(AgoHomeController.class);

    @Autowired
    private DashBoarService dService;

    @GetMapping(value = "")
    public ResponseEntity<ResultBean> dashboard() {
        try {
            ResultBean resultBean = dService.totalReport();
            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
        } catch (ApiValidateException ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

//    @GetMapping(value = "/ref=1")
//    public ResponseEntity<ResultBean> reportUserAccessbyTime() {
//        try {
//            ResultBean resultBean = uService.reportToalUsers();
//            return new ResponseEntity<ResultBean>(resultBean, HttpStatus.CREATED);
//        } catch (ApiValidateException ex) {
//            ex.printStackTrace();
//            return new ResponseEntity<ResultBean>(new ResultBean(ex.getCode(), ex.getMessage()), HttpStatus.OK);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return new ResponseEntity<ResultBean>(new ResultBean(ConstantStatus.STATUS_BAD_REQUEST, ConstantMessage.MESSAGE_SYSTEM_ERROR), HttpStatus.OK);
//        }
//    }
}
