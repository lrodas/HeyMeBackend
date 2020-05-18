package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.EstadoPaquete;

public class CEstadoPaquete {

    public static EstadoPaquete ModelToEntity(com.cycsystems.heymebackend.common.EstadoPaquete model) {
        if (model != null) {
            EstadoPaquete entity = new EstadoPaquete();
            entity.setIdEstadoPaquete(model.getIdEstadoPaquete());
            entity.setDescripcion(model.getDescripcion());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.EstadoPaquete EntityToModel(EstadoPaquete entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.EstadoPaquete model = new com.cycsystems.heymebackend.common.EstadoPaquete();
            model.setIdEstadoPaquete(entity.getIdEstadoPaquete());
            model.setDescripcion(entity.getDescripcion());
            return model;
        } else {
            return null;
        }
    }
}
