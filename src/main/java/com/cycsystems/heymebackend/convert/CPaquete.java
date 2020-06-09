package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Paquete;

public class CPaquete {

    public static Paquete ModelToEntity(com.cycsystems.heymebackend.common.Paquete model) {
        if (model != null) {
            Paquete entity = new Paquete();
            entity.setIdPaquete(model.getIdPaquete());
            entity.setNombre(model.getNombre());
            entity.setEstado(CEstadoPaquete.ModelToEntity(model.getEstadoPaquete()));
            entity.setPrecioGTQ(model.getPrecioGTQ());
            entity.setPrecioUSD(model.getPrecioUSD());
            entity.setDescripcion(model.getDescripcion());
            entity.setIcono(model.getIcono());
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
            model.setPrecioGTQ(entity.getPrecioGTQ());
            model.setPrecioUSD(entity.getPrecioUSD());
            model.setDescripcion(entity.getDescripcion());
            model.setIcono(entity.getIcono());
            return model;
        } else {
            return null;
        }
    }
}
