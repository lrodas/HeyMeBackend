package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IPaqueteDao;
import com.cycsystems.heymebackend.models.entity.Paquete;
import com.cycsystems.heymebackend.models.service.IPaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaqueteServiceImpl implements IPaqueteService {

    @Autowired
    private IPaqueteDao paqueteRepository;

    @Override
    public Paquete save(Paquete entity) {
        return this.paqueteRepository.save(entity);
    }

    @Override
    public Paquete findById(Integer idPaquete) {
        return this.paqueteRepository.findById(idPaquete).get();
    }

    @Override
    public List<Paquete> findAll() {
        return this.paqueteRepository.findAll();
    }

    @Override
    public List<Paquete> findByStatusPackage(Integer idPackage) {
        return this.paqueteRepository.findByEstado_IdEstadoPaquete(idPackage);
    }

    @Override
    public Long count() {
        return this.paqueteRepository.count();
    }
}
