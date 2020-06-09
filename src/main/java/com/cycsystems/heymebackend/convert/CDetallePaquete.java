package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.common.DetallePaquete;

public class CDetallePaquete {

    public static DetallePaquete EntityToModel(com.cycsystems.heymebackend.models.entity.DetallePaquete entity) {
        if (entity != null) {
            DetallePaquete model = new DetallePaquete();
            model.setIdDetallePaquete(entity.getIdDetallePaquete());
            model.setCanal(CCanal.EntityToModel(entity.getCanal()));
            model.setCuota(entity.getCuota());
            model.setPaquete(CPaquete.EntityToModel(entity.getPaquete()));
            return model;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.models.entity.DetallePaquete ModelToEntity(DetallePaquete model) {
        if (model != null) {
            com.cycsystems.heymebackend.models.entity.DetallePaquete entity = new com.cycsystems.heymebackend.models.entity.DetallePaquete();
            entity.setIdDetallePaquete(model.getIdDetallePaquete());
            entity.setCanal(CCanal.ModelToEntity(model.getCanal()));
            entity.setCuota(model.getCuota());
            entity.setPaquete(CPaquete.ModelToEntity(model.getPaquete()));
            return entity;
        } else {
            return null;
        }
    }
}
