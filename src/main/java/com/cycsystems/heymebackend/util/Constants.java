package com.cycsystems.heymebackend.util;

public class Constants {

	public static final String VERSION = "v1";
	
	public static final Integer REQUEST_IN = 1;
	public static final Integer REPLAY_OUT = 2;
	public static final Integer ERROR_OUT = 3;
	public static final Integer NOTIFICACION_TIPO_BUSQUEDA_ESTADO = 1;
	public static final Integer NOTIFICACION_TIPO_BUSQUEDA_TITULO = 2;
	public static final Integer NOTIFICACION_TIPO_BUSQUEDA_USUARIO = 3;
	public static final Integer NOTIFICACION_TIPO_BUSQUEDA_FECHA_PROGRAMACION = 4;
	public static final Integer NOTIFICACION_TIPO_BUSQUEDA_FECHA_ENVIO = 5;
	
	public static final Integer ESTADO_NOTIFICACION_PROGRAMADA = 1;
	public static final Integer ESTADO_NOTIFICACION_ENVIADA = 2;
	public static final Integer ESTADO_NOTIFICACION_CREADA = 3;
	
	public static final Integer CANAL_SMS = 1;
	public static final Integer CANAL_EMAIL = 2;
	public static final Integer CANAL_WHATSAPP = 3;

	public static final String REMITENTE_CORREO = "mail.from";
	public static final String ACCOUNT_SID = "twilio.account.sid";
	public static final String AUTH_TOKEN = "twilio.account.auth.token";
	public static final String SERVICE_ID = "twilio.account.service.id";
	public static final String NOTIFICATION_RATE = "notificacion.tarifa";
	
}
