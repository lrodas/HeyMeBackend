package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.dao.IEmpresaDao;
import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.service.IEmpresaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

	@Autowired
    private IEmpresaDao empresaDao;

    @Override
    public Empresa save(Empresa entity) {
        return this.empresaDao.save(entity);
    }

    @Override
    public Empresa findById(Integer id) {
        if (this.empresaDao.findById(id).isPresent()) {
            return this.empresaDao.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public boolean existsById(Integer id) {
        return this.empresaDao.existsById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return this.empresaDao.existsByCodigo(code);
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

    @Override
    public void removeEmpresa(Empresa empresa) {
        this.empresaDao.delete(empresa);
    }

    @Override
    public Empresa findByCode(String code) {
        return this.empresaDao.findByCodigo(code);
    }

}
