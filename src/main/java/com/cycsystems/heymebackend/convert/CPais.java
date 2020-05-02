package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Pais;

public class CPais {

    public static Pais ModelToEntity(com.cycsystems.heymebackend.common.Pais model) {
        if (model != null) {
            Pais entity = new Pais();
            entity.setIdPais(model.getIdPais());
            entity.setCodigo(model.getCodigo());
            entity.setEstado(model.getEstado());
            entity.setNombre(model.getNombre());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Pais EntityToModel(Pais entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Pais model = new com.cycsystems.heymebackend.common.Pais();
            model.setIdPais(entity.getIdPais());
            model.setCodigo(entity.getCodigo());
            model.setEstado(entity.getEstado());
            model.setNombre(entity.getNombre());
            return model;
        } else {
            return null;
        }
    }
}
