package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.service.IParametroService;
import com.cycsystems.heymebackend.models.service.ITwilioService;
import com.cycsystems.heymebackend.util.Constants;
import com.google.common.collect.Range;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TwilioServiceImpl implements ITwilioService {
    
    @Autowired
    private IParametroService parametroService;

    @Override
    public ResourceSet<Message> mensajesEnviadosPorFecha(Integer idEmpresa, Date fechaInicio, Date fechaFin) {
    	
    	String accountSid = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.ACCOUNT_SID).getValor();
		String authToken = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.AUTH_TOKEN).getValor();
    	
        Twilio.init(accountSid, authToken);
        Calendar calendarFechaInicio = Calendar.getInstance();
        Calendar calendarFechaFin = Calendar.getInstance();
        calendarFechaInicio.setTime(fechaInicio);
        calendarFechaFin.setTime(fechaFin);

        ResourceSet<Message> mensajes = Message.reader()
                .setDateSent(Range.open(
                        new DateTime(calendarFechaInicio.get(Calendar.YEAR), calendarFechaInicio.get(Calendar.MONTH), calendarFechaInicio.get(Calendar.DAY_OF_MONTH), 0, 0, 0),
                        new DateTime(calendarFechaFin.get(Calendar.YEAR), calendarFechaFin.get(Calendar.MONTH), calendarFechaFin.get(Calendar.DAY_OF_MONTH), 0, 0, 0)))
                .read();

        return mensajes;
    }

    @Override
    public ResourceSet<Message> mensajesEnviados(Integer idEmpresa) {

    	String accountSid = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.ACCOUNT_SID).getValor();
		String authToken = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.AUTH_TOKEN).getValor();
    	
    	Twilio.init(accountSid, authToken);
        ResourceSet<Message> mensajes = Message.reader()
                .read();
        return mensajes;
    }

    @Override
    public Double totalCostoMensajes() {
        return null;
    }
}
