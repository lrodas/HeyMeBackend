package com.cycsystems.heymebackend.models.dao;

import com.cycsystems.heymebackend.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionDao extends JpaRepository<Region, Integer> {

}
