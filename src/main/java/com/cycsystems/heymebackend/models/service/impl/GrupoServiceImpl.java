package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IGrupoDao;
import com.cycsystems.heymebackend.models.entity.Grupo;
import com.cycsystems.heymebackend.models.service.IGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoServiceImpl implements IGrupoService {

    @Autowired
    private IGrupoDao grupoDao;

    @Override
    public Grupo save(Grupo grupo) {
        return this.grupoDao.save(grupo);
    }

    @Override
    public Grupo findById(Integer idGrupo) {
        if (this.grupoDao.findById(idGrupo).isPresent()) {
            return this.grupoDao.findById(idGrupo).get();
        } else {
            return null;
        }
    }

    @Override
    public List<Grupo> findAll(Integer idEmpresa) {
        return this.grupoDao.findByEmpresa_IdEmpresa(idEmpresa);
    }

    @Override
    public List<Grupo> findByName(String name, Integer idEmpresa) {
        return this.grupoDao.findByEmpresa_IdEmpresaAndNombreLikeIgnoreCase(idEmpresa, "%" + name + "%");
    }

    @Override
    public Boolean existById(Integer idGrupo) {
        return this.grupoDao.existsById(idGrupo);
    }

    @Override
    public Long count() {
        return this.grupoDao.count();
    }
}
