package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Role;

public class CRole {

    public static Role ModelToEntity(com.cycsystems.heymebackend.common.Role model) {
        if (model != null) {
            Role entity = new Role();
            entity.setIdRole(model.getIdRole());
            entity.setNombre(model.getNombre());
            entity.setEstado(model.getEstado());
            entity.setDescripcion(model.getDescripcion());
            entity.setEmpresa(CEmpresa.ModelToEntity(model.getEmpresa()));
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Role EntityToModel(Role entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Role model = new com.cycsystems.heymebackend.common.Role();
            model.setIdRole(entity.getIdRole());
            model.setNombre(entity.getNombre());
            model.setEstado(entity.getEstado());
            model.setDescripcion(entity.getDescripcion());
            model.setEmpresa(CEmpresa.EntityToModel(entity.getEmpresa()));
            return model;
        } else {
            return null;
        }
    }
}
