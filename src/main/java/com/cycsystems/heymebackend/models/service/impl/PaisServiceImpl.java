package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IPaisDao;
import com.cycsystems.heymebackend.models.entity.Pais;
import com.cycsystems.heymebackend.models.service.IPaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisServiceImpl implements IPaisService {

    @Autowired
    private IPaisDao paisRepository;

    @Override
    public List<Pais> findAllCounties() {
        return this.paisRepository.findByEstado(true);
    }

    @Override
    public List<Pais> findCountryByNameAndEstado(String nombre, Boolean estado) {
        return this.paisRepository.findByNombreLikeIgnoreCaseAndEstado("%" + nombre + "%", estado);
    }

    @Override
    public Pais findCountryById(Integer id) {
        if (this.paisRepository.findById(id).isPresent()) {
            return this.paisRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public List<Pais> findCountryByCodeAndEstado(String code, Boolean estado) {
        return this.paisRepository.findByCodigoLikeIgnoreCaseAndEstado("%" + code + "%", estado);
    }

    @Override
    public Boolean existById(Integer idPais) {
        return this.paisRepository.existsById(idPais);
    }
}
