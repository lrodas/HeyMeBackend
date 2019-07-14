package com.cycsystems.heymebackend.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.Region;

@Repository
public interface IRegionDao extends JpaRepository<Region, Integer> {

}
