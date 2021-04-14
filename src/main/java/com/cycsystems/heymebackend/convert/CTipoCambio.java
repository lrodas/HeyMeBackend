package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.common.TipoCambio;

public class CTipoCambio {

	public static TipoCambio EntityToModel(com.cycsystems.heymebackend.models.entity.TipoCambio entity) {
		if (entity != null) {
			TipoCambio modelo = new TipoCambio();
			modelo.setEstado(entity.getEstado());
			modelo.setFecha(entity.getFecha());
			modelo.setIdTipoCambio(entity.getIdTipoCambio());
			modelo.setValor(entity.getValor());
			return modelo;
		} else {
			return null;
		}
	}

	public static com.cycsystems.heymebackend.models.entity.TipoCambio ModelToEntity(TipoCambio model) {
		if (model != null) {
			com.cycsystems.heymebackend.models.entity.TipoCambio entity = new com.cycsystems.heymebackend.models.entity.TipoCambio();
			entity.setEstado(model.getEstado());
			entity.setFecha(model.getFecha());
			entity.setIdTipoCambio(model.getIdTipoCambio());
			entity.setValor(model.getValor());
			return entity;
		} else {
			return null;
		}
	}

}
