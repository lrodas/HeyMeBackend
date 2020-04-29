package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Canal;

import java.util.List;

public interface ICanalService {

    public abstract Canal save(Canal canal);

    public abstract Canal findById(Integer idCanal);

    public abstract List<Canal> findAll();

    public abstract List<Canal> findByStatus(Boolean status);
}
