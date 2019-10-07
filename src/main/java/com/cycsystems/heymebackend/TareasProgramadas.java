package com.cycsystems.heymebackend;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cycsystems.heymebackend.models.entity.EstadoNotificacion;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import com.cycsystems.heymebackend.models.service.impl.MailServiceImpl;
import com.cycsystems.heymebackend.models.service.impl.SMSServiceImpl;

@Component
public class TareasProgramadas {
	
	// private Logger LOG = LogManager.getLogger(TareasProgramadas.class);
	
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
		
	@Scheduled(fixedDelay = 1000)
	public void taskSendMessage() {
		
		Date fechaActual = new Date();
		
		List<Notificacion> notificaciones = this.notificationService.findbySendingDate(fechaActual, ESTADO_NOTIFICACION_PROGRAMADA);
		
//		for (Notificacion notificacion: notificaciones) {	
//
//			switch(notificacion.getCanal().getIdCanal()) {
//				case 1:
//					
//					this.smsService.sendSMS(
//							notificacion.getDestinatarios().getTelefono(),
//							null,
//							notificacion.getNotificacion());
//					
//					break;
//				case 2:
//					
//					this.mailService.sendMail(
//							this.MAIL_FROM, 
//							notificacion.getDestinatarios().getEmail(),
//							notificacion.getTitulo(),
//							notificacion.getNotificacion());
//					break;
//				case 3:
//					
//					break;
//			}
//			
//			notificacion.setEstado(new EstadoNotificacion(this.ESTADO_NOTIFICACION_ENVIADA));
//			
//			this.notificationService.save(notificacion);
//		}
		
	}

}
