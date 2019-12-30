package com.cycsystems.heymebackend.models.service;

import com.cycsystems.heymebackend.models.entity.Pais;

import java.util.List;

public interface IPaisService {
    public List<Pais> findAllCounties();
    public List<Pais> findCountryByNameAndEstado(String nombre, Boolean estado);
    public Pais findCountryById(Integer id);
    public List<Pais> findCountryByCodeAndEstado(String code, Boolean estado);

}
