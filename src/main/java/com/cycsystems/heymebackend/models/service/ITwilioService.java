package com.cycsystems.heymebackend.models.service;

import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Date;

public interface ITwilioService {

    public ResourceSet<Message> mensajesEnviadosPorFecha(Integer idEmpresa, Date fechaInicio, Date fechaFin);
    public ResourceSet<Message> mensajesEnviados(Integer idEmpresa);
    public Double totalCostoMensajes();

}
