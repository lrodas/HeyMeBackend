package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Notificacion;

public class CNotificacion {

    public static Notificacion ModelToEntity(com.cycsystems.heymebackend.common.Notificacion model) {
        if (model != null) {
            Notificacion entity = new Notificacion();
            entity.setIdNotificaciones(model.getIdNotificaciones());
            entity.setEstado(CEstadoNotificacion.ModelToEntity(model.getEstado()));
            entity.setCodigo(model.getCodigo());
            entity.setFechaEnvio(model.getFechaEnvio());
            entity.setCanal(CCanal.ModelToEntity(model.getCanal()));
            entity.setEmpresa(CEmpresa.ModelToEntity(model.getEmpresa()));
            entity.setEstadoPago(model.getEstadoPago());
            entity.setNotificacion(model.getNotificacion());
            entity.setTitulo(model.getTitulo());
            entity.setUsuario(CUsuario.ModelToEntity(model.getUsuario()));
            entity.setFechaProgramacion(model.getFechaProgramacion());
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Notificacion EntityToModel(Notificacion entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Notificacion model = new com.cycsystems.heymebackend.common.Notificacion();
            model.setIdNotificaciones(entity.getIdNotificaciones());
            model.setEstado(CEstadoNotificacion.EntityToModel(entity.getEstado()));
            model.setCodigo(entity.getCodigo());
            model.setFechaEnvio(entity.getFechaEnvio());
            model.setCanal(CCanal.EntityToModel(entity.getCanal()));
            model.setEmpresa(CEmpresa.EntityToModel(entity.getEmpresa()));
            model.setEstadoPago(entity.getEstadoPago());
            model.setNotificacion(entity.getNotificacion());
            model.setTitulo(entity.getTitulo());
            model.setUsuario(CUsuario.EntityToModel(entity.getUsuario()));
            model.setFechaProgramacion(entity.getFechaProgramacion());
            return model;
        } else {
            return null;
        }
    }
}
