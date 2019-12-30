package com.cycsystems.heymebackend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cycsystems.heymebackend.models.entity.DetallePaquete;

@Repository
public interface IDetallePaqueteDao extends JpaRepository<DetallePaquete, Integer>{
	
	public List<DetallePaquete> findByPaquete_IdPaquete(Integer idPaquete);

}
