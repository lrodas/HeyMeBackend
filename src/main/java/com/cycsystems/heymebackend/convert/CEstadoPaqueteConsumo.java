package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.EstadoPaqueteConsumo;

public class CEstadoPaqueteConsumo {

    public static EstadoPaqueteConsumo ModelToEntity(com.cycsystems.heymebackend.common.EstadoPaqueteConsumo model) {
        if (model != null) {
            EstadoPaqueteConsumo entity = new EstadoPaqueteConsumo();
            entity.setIdEstadoPaqueteConsumo(model.getIdEstadoPaqueteConsumo());
            entity.setDescripcion(model.getDescripcion());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.EstadoPaqueteConsumo EntityToModel(EstadoPaqueteConsumo entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.EstadoPaqueteConsumo model = new com.cycsystems.heymebackend.common.EstadoPaqueteConsumo();
            model.setIdEstadoPaqueteConsumo(entity.getIdEstadoPaqueteConsumo());
            model.setDescripcion(entity.getDescripcion());
            return model;
        } else {
            return null;
        }
    }
}
