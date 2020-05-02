package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Permiso;

public class CPermiso {

    public static Permiso ModelToEntity(com.cycsystems.heymebackend.common.Permiso model) {
        if (model != null) {
            Permiso entity = new Permiso();
            entity.setIdPermiso(model.getIdPermiso());
            entity.setPuesto(CRole.ModelToEntity(model.getPuesto()));
            entity.setOpcion(COpcion.ModelToEntity(model.getOpcion()));
            entity.setAlta(model.isAlta());
            entity.setBaja(model.isBaja());
            entity.setCambio(model.isCambio());
            entity.setImprimir(model.isImprimir());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Permiso EntityToModel(Permiso entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Permiso model = new com.cycsystems.heymebackend.common.Permiso();
            model.setIdPermiso(entity.getIdPermiso());
            model.setPuesto(CRole.EntityToModel(entity.getPuesto()));
            model.setOpcion(COpcion.EntityToModel(entity.getOpcion()));
            model.setAlta(entity.isAlta());
            model.setBaja(entity.isBaja());
            model.setCambio(entity.isCambio());
            model.setImprimir(entity.isImprimir());
            return model;
        } else {
            return null;
        }
    }
}
