package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.common.Consumo;
import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;

public class CConsumo {

    public static PaqueteConsumo ModelToEntity(Consumo model) {
        if (model != null) {
            PaqueteConsumo entity = new PaqueteConsumo();
            entity.setIdPaqueteConsumo(model.getIdPaqueteConsumo());
            entity.setPaquete(CPaquete.ModelToEntity(model.getPaquete()));
            entity.setConsumoWhatsapp(model.getConsumoWhatsapp());
            entity.setConsumoSMS(model.getConsumoSMS());
            entity.setConsumoMAIL(model.getConsumoEmail());
            entity.setEmpresa(CEmpresa.ModelToEntity(model.getEmpresa()));
            entity.setFechaFin(model.getFechaFin());
            entity.setFechaInicio(model.getFechaInicio());
            entity.setEstado(CEstadoPaqueteConsumo.ModelToEntity(model.getEstado()));
            return entity;
        } else {
            return null;
        }
    }

    public static Consumo EntityToModel(PaqueteConsumo entity) {
        if (entity != null) {
            Consumo model = new Consumo();
            model.setIdPaqueteConsumo(entity.getIdPaqueteConsumo());
            model.setPaquete(CPaquete.EntityToModel(entity.getPaquete()));
            model.setConsumoWhatsapp(entity.getConsumoWhatsapp());
            model.setConsumoEmail(entity.getConsumoMAIL());
            model.setConsumoSMS(entity.getConsumoSMS());
            model.setEmpresa(CEmpresa.EntityToModel(entity.getEmpresa()));
            model.setFechaFin(entity.getFechaFin());
            model.setFechaInicio(entity.getFechaInicio());
            model.setEstado(CEstadoPaqueteConsumo.EntityToModel(entity.getEstado()));
            return model;
        } else {
            return null;
        }
    }
}
