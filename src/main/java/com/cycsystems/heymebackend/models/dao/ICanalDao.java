package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Canal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICanalDao extends JpaRepository<Canal, Integer> {

    public abstract List<Canal> findByEstado(Boolean estado);


}
