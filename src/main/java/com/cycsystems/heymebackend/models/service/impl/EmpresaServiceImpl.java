package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IEmpresaDao;
import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.service.IEmpresaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

    private IEmpresaDao empresaDao;

    @Override
    public Empresa save(Empresa entity) {
        return this.empresaDao.save(entity);
    }

    @Override
    public Empresa findById(Integer id) {
        return this.empresaDao.findById(id).get();
    }

    @Override
    public boolean existsById(Integer id) {
        return this.empresaDao.existsById(id);
    }

    @Override
    public List<Empresa> findAll() {
        return this.empresaDao.findAll();
    }

    @Override
    public long count() {
        return this.empresaDao.count();
    }

    @Override
    public List<Empresa> findByName(String name) {
        return null;
    }
}
