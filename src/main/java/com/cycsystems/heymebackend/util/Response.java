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
	NOTIFICATION_ALREADY_SENT("0024", "La notificacion ya fue enviada, no es posible cancelarla", "ERROR"),
	PACKAGE_NOT_EMPTY("0025", "Debe enviar el paquete que desea asignar", "ERROR"),
	USER_NOT_EMPTY("0026", "Debe especificar el usuario", "ERROR"),
	TEMPLATE_NOT_EMPTY("0027", "Debe enviar la informacion de la plantilla", "ERROR"),
	TEMPLATE_NOT_EXISTS("0028", "No se encuentra la plantilla con el identificador enviado", "ERROR"),
	TEMPLATE_STATUS_NOT_EMPTY("0029", "Debe enviar el estado de la plantilla", "ERROR"),
	TEMPLATE_TITLE_NOT_EMPTY("0030", "Debe enviar el titulo de la plantilla", "ERROR"),
	TEMPLATE_CONTENT_NOT_EMPTY("0031", "Debe enviar el contenido de la plantilla", "ERROR"),
	COUNTRY_CONTENT_EMPTY("0032", "Debes enviar el contenido del pais", "ERROR"),
	COUNTRY_ID_EMPTY("0033", "Debes enviar el identificador del pais", "ERROR"),
	COUNTRY_NOT_EXIST("0034", "El pais no existe", "ERROR"),
	COUNTRY_NAME_NOT_EMPTY("0035", "Debe enviar el nombre del pais", "ERROR"),
	COUNTRY_CODE_NOT_EMPTY("0036", "Debe enviar el codigo del pais", "ERROR"),
	CONTACT_NAME_NOT_EMPTY("0037", "El nombre del contacto es obligatorio", "ERROR"),
	CONTACT_LAST_NAME_NOT_EMPTY("0038", "El apellido del contacto es obligatorio", "ERROR"),
	CONTACT_ADDRESS_NOT_EMPTY("0039", "La direccion del contacto es obligatoria", "ERROR"),
	CONTACT_REGION_NOT_EMPTY("0040", "La region a la que pertenece el contacto es obligatorio", "ERROR"),
	CONTACT_PHONE_NOT_EMPTY("0041", "El telefono del contacto es obligatorio", "ERROR"),
	CONTACT_REGION_NOT_EXIST("0042", "La region enviada no existe, por favor verificar", "ERROR"),
	CONTACT_NOT_EMPTY("0043", "Es necesario enviar los datos del contacto", "ERROR"),
	CONTACT_ID_NOT_EMPTY("0044", "Es necesario enviar el identificador del contacto", "ERROR"),
	CONTACT_NOT_EXIST("0045", "No existe un contacto con el identificador enviado", "ERROR"),
	CONTACT_STATUS_NOT_EMPTY("0046", "Es necesario enviar el estado del usuario", "ERROR"),
	START_DATE_NOT_EMPTY("0047", "La fecha de inicio es obligatoria", "ERROR"),
	END_DATE_NOT_EMPTY("0048", "La fecha de fin es obligatoria", "ERROR"),
	START_DATE_BEFORE_END_DATE("0049", "La fecha de inicio debe ser menor a la fecha de fin", "ERROR"),
	COUNTRY_NOT_EMPTY("0050", "El pais del numero telefonico es obligatorio", "ERROR"),
	PACKAGE_STATUS_NOT_EMPTY("0051", "Debe enviar el estado del paquete", "ERROR"),
	PACKAGE_END_DATE_NOT_EMPTY("0052", "Debe enviar la fecha de finalizacion de vigencia del paquete", "ERROR"),
	NOTIFICATION_STATUS_NOT_EMPTY("0053", "Debe enviar el titulo de la notificacion", "ERROR");

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
