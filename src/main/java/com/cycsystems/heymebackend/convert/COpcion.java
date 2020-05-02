package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Opcion;

public class COpcion {

    public static Opcion ModelToEntity(com.cycsystems.heymebackend.common.Opcion model) {
        if (model != null) {
            Opcion entity = new Opcion();
            entity.setIdOpcion(model.getIdOpcion());
            entity.setDescripcion(model.getDescripcion());
            entity.setIcono(model.getIcono());
            entity.setOrden(model.getOrden());
            entity.setUrl(model.getUrl());
            entity.setEvento(model.isEvento());
            entity.setMostrar(model.isMostrar());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Opcion EntityToModel(Opcion entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Opcion model = new com.cycsystems.heymebackend.common.Opcion();
            model.setIdOpcion(entity.getIdOpcion());
            model.setDescripcion(entity.getDescripcion());
            model.setIcono(entity.getIcono());
            model.setOrden(entity.getOrden());
            model.setUrl(entity.getUrl());
            model.setEvento(entity.isEvento());
            model.setMostrar(entity.isMostrar());
            return model;
        } else {
            return null;
        }
    }
}
