package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.service.ITwilioService;
import com.google.common.collect.Range;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TwilioServiceImpl implements ITwilioService {

    private static final Logger LOG = LogManager.getLogger(SMSServiceImpl.class);

    @Value("${twilio.account.service.id}")
    private String SERVICE_ID;

    @Value("${twilio.account.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Override
    public ResourceSet<Message> mensajesEnviadosPorFecha(Date fechaInicio, Date fechaFin) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
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
    public ResourceSet<Message> mensajesEnviados() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        ResourceSet<Message> mensajes = Message.reader()
                .read();
        return mensajes;
    }

    @Override
    public Double totalCostoMensajes() {
        return null;
    }
}
