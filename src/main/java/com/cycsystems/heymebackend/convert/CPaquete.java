package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Paquete;

public class CPaquete {

    public static Paquete ModelToEntity(com.cycsystems.heymebackend.common.Paquete model) {
        if (model != null) {
            Paquete entity = new Paquete();
            entity.setIdPaquete(model.getIdPaquete());
            entity.setNombre(model.getNombre());
            entity.setEstado(CEstadoPaquete.ModelToEntity(model.getEstadoPaquete()));
            entity.setPrecio(model.getPrecio());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Paquete EntityToModel(Paquete entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Paquete model = new com.cycsystems.heymebackend.common.Paquete();
            model.setIdPaquete(entity.getIdPaquete());
            model.setNombre(entity.getNombre());
            model.setEstadoPaquete(CEstadoPaquete.EntityToModel(entity.getEstado()));
            model.setPrecio(entity.getPrecio());
            return model;
        } else {
            return null;
        }
    }
}
