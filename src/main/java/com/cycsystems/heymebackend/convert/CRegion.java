package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Region;

public class CRegion {

    public static Region ModelToEntity(com.cycsystems.heymebackend.common.Region model) {
        if (model != null) {
            Region entity = new Region();
            entity.setIdRegion(model.getIdRegion());
            entity.setNombre(model.getNombre());
            entity.setPais(CPais.ModelToEntity(model.getPais()));
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Region EntityToModel(Region entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Region model = new com.cycsystems.heymebackend.common.Region();
            model.setIdRegion(entity.getIdRegion());
            model.setNombre(entity.getNombre());
            model.setPais(CPais.EntityToModel(entity.getPais()));
            return model;
        } else {
            return null;
        }
    }
}
