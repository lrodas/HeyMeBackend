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
	public static final Integer ESTADO_NOTIFICACION_ACEPTADA = 4;
	public static final Integer ESTADO_NOTIFICACION_EN_COLA = 5;
	public static final Integer ESTADO_NOTIFICACION_CANCELADA = 6;

	public static final Integer CANAL_SMS = 1;
	public static final Integer CANAL_EMAIL = 2;
	public static final Integer CANAL_WHATSAPP = 3;

	public static final Integer ESTADO_PAQUETE_CONSUMO_ACTIVO = 1;
	public static final Integer ESTADO_PAQUETE_CONSUMO_VENCIDO = 2;
	public static final Integer ESTADO_PAQUETE_CONSUMO_INACTIVO = 3;

	public static final Integer ESTADO_PAQUETE_ACTIVO = 1;
	public static final Integer ESTADO_PAQUETE_INACTIVO = 2;
	public static final Integer ESTADO_PAQUETE_SUSPENDIDO = 3;

	public static final String REMITENTE_CORREO = "mail.from";
	public static final String ACCOUNT_SID = "twilio.account.sid";
	public static final String AUTH_TOKEN = "twilio.account.auth.token";
	public static final String SERVICE_ID = "twilio.account.service.id";
	public static final String NOTIFICATION_RATE = "notificacion.tarifa";
	public static final String IMAGES_URL = "images.url";

	public static final String AWSADMINUSER = "AWSAdminUser";
	public static final String AWSADMINKEY = "AWSAdminKey";
	public static final String AWSADMINREGION = "AWSRegion";

	public static final String[] DATE_FORMATS = { "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
			"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			"yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy'T'HH:mm:ss.SSSZ",
			"MM/dd/yyyy'T'HH:mm:ss.SSS", "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss", "yyyy:MM:dd HH:mm:ss",
			"yyyyMMdd", };

}
