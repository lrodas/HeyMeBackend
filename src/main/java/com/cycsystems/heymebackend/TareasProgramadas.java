package com.cycsystems.heymebackend;

import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.EstadoNotificacion;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import com.cycsystems.heymebackend.models.service.impl.MailServiceImpl;
import com.cycsystems.heymebackend.models.service.impl.SMSServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TareasProgramadas {
	
	private Logger LOG = LogManager.getLogger(TareasProgramadas.class);
	
	@Autowired
	private INotificacionService notificationService;
	
	@Autowired
	private SMSServiceImpl smsService;
	
	@Autowired
	private MailServiceImpl mailService;
	
	@Value("${estado.notificacion.programada}")
	private Integer ESTADO_NOTIFICACION_PROGRAMADA;
	
	@Value("${estado.notificacion.enviada}")
	private Integer ESTADO_NOTIFICACION_ENVIADA;
	
	@Value("${mail.from}")
	private String MAIL_FROM;

	@Value("${canal.sms}")
	private Integer CANAL_SMS;

	@Value("${canal.mail}")
	private Integer CANAL_MAIL;

	@Value("${canal.whatsapp}")
	private Integer CANAL_WHATSAPP;
		
	// @Scheduled(cron = "0 0 0/12 * * *")
	@Scheduled(fixedDelay = 1000)
	public void taskSendMessage() {

		LOG.info("METHOD: taskSendMessage()");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date fechaActual = calendar.getTime();
		LOG.info("Fecha actual: " + fechaActual);
		List<Notificacion> notificaciones = this.notificationService.findbySendingDate(fechaActual, ESTADO_NOTIFICACION_PROGRAMADA);
		LOG.info("notificaciones obtenidas : " + notificaciones.size());
		for (Notificacion notificacion : notificaciones) {
			String codigo = "";
			for (Contacto contacto : notificacion.getDestinatarios()) {
				if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_SMS) == 0) {
					codigo = this.smsService.sendSMS("+502" + contacto.getTelefono(), notificacion.getNotificacion());
					LOG.info("notificacion enviada exitosamente: codigo " + codigo);
				} else if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_MAIL) == 0) {
					this.mailService.sendMail(this.MAIL_FROM, contacto.getEmail(), notificacion.getTitulo(), notificacion.getNotificacion());
				} else if (notificacion.getCanal().getIdCanal().compareTo(this.CANAL_WHATSAPP) == 0) {
				}
			}
			notificacion.setCodigo(codigo);
			notificacion.setEstado(new EstadoNotificacion(this.ESTADO_NOTIFICACION_ENVIADA));
			LOG.info("Guardando notificacion: " + notificacion.getIdNotificaciones());
			this.notificationService.save(notificacion);
		}
	}
}
