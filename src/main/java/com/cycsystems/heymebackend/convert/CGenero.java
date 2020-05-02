package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Genero;

public class CGenero {

    public static Genero ModelToEntity(com.cycsystems.heymebackend.common.Genero model) {
        if (model != null) {
            Genero entity = new Genero();
            entity.setIdGenero(model.getIdGenero());
            entity.setDescripcion(model.getDescripcion());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Genero EntityToModel(Genero entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Genero model = new com.cycsystems.heymebackend.common.Genero();
            model.setIdGenero(entity.getIdGenero());
            model.setDescripcion(entity.getDescripcion());
            return model;
        } else {
            return null;
        }
    }
}
