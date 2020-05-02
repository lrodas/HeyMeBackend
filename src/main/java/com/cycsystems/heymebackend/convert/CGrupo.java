package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Grupo;

public class CGrupo {

    public static Grupo ModelToEntity(com.cycsystems.heymebackend.common.Grupo model) {
        if (model != null) {
            Grupo entity = new Grupo();
            entity.setIdGrupo(model.getIdGrupo());
            entity.setNombre(model.getNombre());
            entity.setEmpresa(CEmpresa.ModelToEntity(model.getEmpresa()));
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Grupo EntityToModel(Grupo entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Grupo model = new com.cycsystems.heymebackend.common.Grupo();
            model.setIdGrupo(entity.getIdGrupo());
            model.setNombre(entity.getNombre());
            model.setEmpresa(CEmpresa.EntityToModel(entity.getEmpresa()));
            return model;
        } else {
            return null;
        }
    }
}
