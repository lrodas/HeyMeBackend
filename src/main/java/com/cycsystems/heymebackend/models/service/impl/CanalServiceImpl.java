package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.ICanalDao;
import com.cycsystems.heymebackend.models.entity.Canal;
import com.cycsystems.heymebackend.models.service.ICanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanalServiceImpl implements ICanalService {

    @Autowired
    private ICanalDao canalRepository;

    @Override
    public Canal save(Canal canal) {
        return this.canalRepository.save(canal);
    }

    @Override
    public Canal findById(Integer idCanal) {
        if (this.canalRepository.findById(idCanal).isPresent()) {
            return this.canalRepository.findById(idCanal).get();
        } else {
            return null;
        }
    }

    @Override
    public List<Canal> findAll() {
        return this.canalRepository.findAll();
    }

    @Override
    public List<Canal> findByStatus(Boolean status) {
        return this.canalRepository.findByEstado(status);
    }
}
