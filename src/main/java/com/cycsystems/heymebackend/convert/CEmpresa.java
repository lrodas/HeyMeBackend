package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Empresa;

public class CEmpresa {

    public static Empresa ModelToEntity (com.cycsystems.heymebackend.common.Empresa modelo) {
        if (modelo != null) {
            Empresa empresa = new Empresa();
            empresa.setIdEmpresa(modelo.getIdEmpresa());
            empresa.setNombreEmpresa(modelo.getNombreEmpresa());
            empresa.setDireccion(modelo.getDireccion());
            empresa.setTelefono(modelo.getTelefono());
            empresa.setCodigo(modelo.getCodigo());
            empresa.setLogo(modelo.getLogo());
            return empresa;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Empresa EntityToModel(Empresa entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Empresa modelo = new com.cycsystems.heymebackend.common.Empresa();
            modelo.setIdEmpresa(entity.getIdEmpresa());
            modelo.setNombreEmpresa(entity.getNombreEmpresa());
            modelo.setDireccion(entity.getDireccion());
            modelo.setTelefono(entity.getTelefono());
            modelo.setCodigo(entity.getCodigo());
            modelo.setLogo(entity.getLogo());
            return modelo;
        } else {
            return null;
        }
    }
}
