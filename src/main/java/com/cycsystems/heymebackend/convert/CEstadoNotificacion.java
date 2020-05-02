package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.EstadoNotificacion;

public class CEstadoNotificacion {

    public static EstadoNotificacion ModelToEntity(com.cycsystems.heymebackend.common.EstadoNotificacion model) {
        if (model != null) {
            EstadoNotificacion entity = new EstadoNotificacion();
            entity.setIdEstadoNotificacion(model.getIdEstadoNotificacion());
            entity.setDescripcion(model.getDescripcion());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.EstadoNotificacion EntityToModel(EstadoNotificacion entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.EstadoNotificacion model = new com.cycsystems.heymebackend.common.EstadoNotificacion();
            model.setIdEstadoNotificacion(entity.getIdEstadoNotificacion());
            model.setDescripcion(entity.getDescripcion());
            return model;
        } else {
            return null;
        }
    }
}
