package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Canal;

public class CCanal {

    public static Canal ModelToEntity(com.cycsystems.heymebackend.common.Canal model) {
        if (model != null) {
            Canal entity = new Canal();
            entity.setIdCanal(model.getIdCanal());
            entity.setNombre(model.getNombre());
            entity.setEstado(model.getEstado());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Canal EntityToModel(Canal entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Canal model = new com.cycsystems.heymebackend.common.Canal();
            model.setIdCanal(entity.getIdCanal());
            model.setNombre(entity.getNombre());
            model.setEstado(entity.getEstado());
            return model;
        } else {
            return null;
        }
    }
}
