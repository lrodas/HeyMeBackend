package com.cycsystems.heymebackend.util;

public enum Response {
	
	SUCCESS_RESPONSE("0000", "Operacion realizada exitosamente", "SUCCESS"),
	NOMBRE_USUARIO_ERROR("0001", "El nombre del usuario es obligatorio", "ERROR"),
	APELLIDO_USUARIO_ERROR("0002", "El apellido del usuario es obligatorio", "ERROR"),
	DIRECCION_USUARIO_ERROR("0003", "La direccion del usuario es obligatoria", "ERROR"),
	TELEFONO_USUARIO_ERROR("0004", "El telefono del usuario es obligatoria", "ERROR"),
	GENERO_ERROR("0005", "El genero del usuario es obligatorio", "ERROR"),
	ROLE_ERROR("0006", "El role del usuario es obligatorio", "ERROR"),
	CORREO_ERROR("0007", "El correo del usuario es obligatorio", "ERROR"),
	PASSWORD_ERROR("0008", "La contrasena del usuario es obligatoria", "ERROR"),
	PASSWORD_VALIDATION("0009", "Debe enviar la nueva contrasena para la validacion", "ERROR"),
	ID_USUARIO_ERROR("0010", "Debe especificar el identificador usuario", "ERROR"),
	PASSWORD_DONT_MATCH_ERROR("0011", "La contrasena es incorrecta", "ERROR"),
	USER_NOT_EXIST_ERROR("0012", "El usuario no existe", "ERROR"),
	IMAGE_NOT_EMPTY_ERROR("0013", "Debes enviar una imagen a guardar", "ERROR"),
	IMAGE_COULD_NOT_SAVE_ERROR("0014", "En estos momentos no es posible guardar la imagen", "ERROR"),
	NOTIFICATION_TITLE_ERROR("0015", "Debe enviar el titulo de la notificacion", "ERROR"),
	NOTIFICATION_DATE_ERROR("0016", "Se debe enviar la fecha en que se enviara la notificacion", "ERROR"),
	NOTIFICATION_CONTENT_ERROR("0017", "Se debe enviar el contenido de la notificacion", "ERROR"),
	NOTIFICATION_CANAL_ERROR("0018", "Se debe enviar el canal", "ERROR"),
	NOTIFICATION_CONTACT_ERROR("0019", "El contacto enviado no existe", "ERROR"),
	NOTIFICATION_ID_ERROR("0020", "Se debe enviar el identificador de la notificacion", "ERROR"),
	PACKAGE_NOT_AVAILABE("0021", "No cuentas con un paquete activo", "ERROR"),
	NOTIFICATIONS_NOT_AVAILABLE("0022", "Tu paquete de notificaciones se ha agotado, por favor contacta con servicio al cliente", "ERROR"),
	NOTIFICATION_NOT_EXISTS("0023", "La notificacion especificada no existe", "ERROR"),
	NOTIFICATION_ALREADY_SENT("0024", "La notificacion ya fue enviada, no es posible cancelarla", "ERROR");
	
	private String codigo;
	private String message;
	private String indicador;

	private Response(String codigo, String message, String indicador) {
		this.codigo = codigo;
		this.message = message;
		this.indicador = indicador;
	}
	
	private Response(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getMessage() {
		return message;
	}

	public String getIndicador() {
		return indicador;
	}

}
