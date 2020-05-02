package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.EstadoUsuario;

public class CEstadoUsuario {

    public static EstadoUsuario ModelToEntity(com.cycsystems.heymebackend.common.EstadoUsuario model) {
        if (model != null) {
            EstadoUsuario entity = new EstadoUsuario();
            entity.setIdEstadoUsuario(model.getIdEstadoUsuario());
            entity.setDescripcion(model.getDescripcion());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.EstadoUsuario EntityToModel(EstadoUsuario entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.EstadoUsuario model = new com.cycsystems.heymebackend.common.EstadoUsuario();
            model.setIdEstadoUsuario(entity.getIdEstadoUsuario());
            model.setDescripcion(entity.getDescripcion());
            return model;
        } else {
            return null;
        }
    }
}
