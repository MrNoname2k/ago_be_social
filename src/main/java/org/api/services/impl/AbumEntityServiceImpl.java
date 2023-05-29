package org.api.services.impl;

import org.api.annotation.LogExecutionTime;
import org.api.entities.AbumEntity;
import org.api.repository.AbumEntityRepository;
import org.api.services.AbumEntityService;
import org.api.utils.ApiValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@LogExecutionTime
@Service
@Transactional(rollbackFor = { ApiValidateException.class, Exception.class })
public class AbumEntityServiceImpl implements AbumEntityService {

    public static final String ALIAS = "Abum";

    @Autowired
    private AbumEntityRepository abumEntityRepository;

    @Override
    public AbumEntity createAbumDefault(String type){
        AbumEntity entity = new AbumEntity();
        entity.setName(type);
        entity.setTypeAbum(type);
        return abumEntityRepository.save(entity);
    }

    @Override
    public Boolean existsByTypeAbum(String tpeAbum) {
        if(Boolean.TRUE.equals(abumEntityRepository.existsByTypeAbum(tpeAbum))){
            return true;
        }
        return false;
    }

    @Override
    public AbumEntity findOneById(String id) {
        AbumEntity entity = abumEntityRepository.findById(id).get();
        return entity;
    }

    @Override
    public Optional<AbumEntity> findOneByTypeAbum(String tpeAbum) {
        Optional<AbumEntity> entity = abumEntityRepository.findOneByTypeAbum(tpeAbum);
        return entity;
    }
}
