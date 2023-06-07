package org.api.services.impl;

import com.google.gson.JsonObject;
import org.api.annotation.LogExecutionTime;
import org.api.component.JwtTokenProvider;
import org.api.constants.*;
import org.api.entities.UserEntity;
import org.api.entities.UserRoleEntity;
import org.api.payload.ResultBean;
import org.api.repository.RoleRepository;
import org.api.repository.UserEntityRepository;
import org.api.services.AuthenticationService;
import org.api.services.CustomUserDetailsService;
import org.api.services.UserEntityService;
import org.api.utils.ApiValidateException;
import org.api.utils.DataUtil;
import org.api.utils.MessageUtils;
import org.api.utils.ValidateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@LogExecutionTime
@Service
@Transactional(rollbackFor = {ApiValidateException.class, Exception.class})
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResultBean loginAuth(String json) throws ApiValidateException, Exception {
        UserEntity entity = new UserEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_LOGIN_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntityLogin(jsonObject, entity);
        if (Boolean.FALSE.equals(userEntityRepository.existsByMail(entity.getMail()))) {
            throw new ApiValidateException(ConstantMessage.ID_ERR00004, MessageUtils.getMessage(ConstantMessage.ID_ERR00004));
        }
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(entity.getMail(), entity.getPassword()));
        } catch (Exception ex) {
            ex.printStackTrace();
//            throw new ApiValidateException(ConstantMessage.ID_AUTH_ERR00001, MessageUtils.getMessage(ConstantMessage.ID_AUTH_ERR00001));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetailsService userDetails = (CustomUserDetailsService) authentication.getPrincipal();
        String token = tokenProvider.generateJwtToken(authentication);
        UserEntity entityOld = userEntityService.updateLastLogin(userDetails.getUsername());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantColumns.USER_ENTITY, entityOld);
        map.put(ConstantColumns.TOKEN, token);
        return new ResultBean(map, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public ResultBean tokenAuth(String token) throws ApiValidateException, Exception {
        return null;
    }

    @Override
    public ResultBean registerAuth(String json) throws ApiValidateException, Exception {
        UserEntity entity = new UserEntity();
        JsonObject jsonObject = DataUtil.getJsonObject(json);
        ValidateData.validate(ConstantJsonFileValidate.FILE_REGISTER_JSON_VALIDATE, jsonObject, false);
        this.convertJsonToEntityRegister(jsonObject, entity);
//        entity.setRole(ConstantRole.ROLE_USER);
        UserRoleEntity userRole = this.roleRepository.findByAuthority(ConstantRole.ROLE_USER);
        Set<UserRoleEntity> roles = new HashSet<>();
        roles.add(userRole);
        entity.setAuthorities(roles);

        if (Boolean.TRUE.equals(userEntityRepository.existsByMail(entity.getMail()))) {
            throw new ApiValidateException(ConstantMessage.ID_ERR00001, MessageUtils.getMessage(ConstantMessage.ID_ERR00001));
        }
        userEntityRepository.save(entity);
        return new ResultBean(entity, ConstantStatus.STATUS_OK, ConstantMessage.MESSAGE_OK);
    }

    @Override
    public UserEntity authentication() throws ApiValidateException, Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userPrincipal = (CustomUserDetailsService) authentication.getPrincipal();
        UserEntity entityOld = userEntityService.findOneByMail(userPrincipal.getUsername());
        return entityOld;
    }

    private void convertJsonToEntityRegister(JsonObject json, UserEntity entity) throws ApiValidateException {
        if (DataUtil.hasMember(json, ConstantColumns.FULL_NAME)) {
            entity.setFullName(DataUtil.getJsonString(json, ConstantColumns.FULL_NAME));
        }
        if (DataUtil.hasMember(json, ConstantColumns.MAIL)) {
            entity.setMail(DataUtil.getJsonString(json, ConstantColumns.MAIL));
        }
        if (DataUtil.hasMember(json, ConstantColumns.PASSWORD)) {
            entity.setPassword(encoder.encode(DataUtil.getJsonString(json, ConstantColumns.PASSWORD)));
        }
//        if (DataUtil.hasMember(json, ConstantColumns.ROLE)) {
//            entity.setRole(DataUtil.getJsonString(json, ConstantColumns.ROLE));
//        }
    }

    private void convertJsonToEntityLogin(JsonObject json, UserEntity entity) throws ApiValidateException {
        if (DataUtil.hasMember(json, ConstantColumns.MAIL)) {
            entity.setMail(DataUtil.getJsonString(json, ConstantColumns.MAIL));
        }
        if (DataUtil.hasMember(json, ConstantColumns.PASSWORD)) {
            entity.setPassword(DataUtil.getJsonString(json, ConstantColumns.PASSWORD));
        }
    }
}
