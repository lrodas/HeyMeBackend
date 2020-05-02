package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Provincia;

public class CProvincia {

    public static Provincia ModelToEntity(com.cycsystems.heymebackend.common.Provincia model) {
        if (model != null) {
            Provincia entity = new Provincia();
            entity.setIdProvincia(model.getIdProvincia());
            entity.setNombre(model.getNombre());
            entity.setRegion(CRegion.ModelToEntity(model.getRegion()));
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Provincia EntityToModel(Provincia entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Provincia model = new com.cycsystems.heymebackend.common.Provincia();
            model.setIdProvincia(entity.getIdProvincia());
            model.setNombre(entity.getNombre());
            model.setRegion(CRegion.EntityToModel(entity.getRegion()));
            return model;
        } else {
            return null;
        }
    }
}
