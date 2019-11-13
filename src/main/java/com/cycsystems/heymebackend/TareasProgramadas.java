package com.cycsystems.heymebackend;

import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.EstadoNotificacion;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.service.IEmpresaService;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import com.cycsystems.heymebackend.models.service.IParametroService;
import com.cycsystems.heymebackend.models.service.impl.MailServiceImpl;
import com.cycsystems.heymebackend.models.service.impl.SMSServiceImpl;
import com.cycsystems.heymebackend.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TareasProgramadas {
	
	// private Logger LOG = LogManager.getLogger(TareasProgramadas.class);
	
	@Autowired
	private INotificacionService notificationService;
	
	@Autowired
	private SMSServiceImpl smsService;
	
	@Autowired
	private MailServiceImpl mailService;
	
	@Autowired
	private IEmpresaService empresaService;
	
	@Autowired
	private IParametroService parametroService;
		
	// @Scheduled(cron = "0 0 0/12 * * *")
	@Scheduled(fixedDelay = 1000)
	public void taskSendMessage() {

		List<Empresa> empresas = this.empresaService.findAll();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date fechaActual = calendar.getTime();
		
		for (Empresa empresa: empresas) {
		
			List<Notificacion> notificaciones = this.notificationService.findbySendingDate(fechaActual, Constants.ESTADO_NOTIFICACION_PROGRAMADA, empresa.getIdEmpresa());
			String mailFrom = this.parametroService.findParameterByEmpresaAndName(empresa.getIdEmpresa(), Constants.REMITENTE_CORREO).getValor();
			
			for (Notificacion notificacion : notificaciones) {
				String codigo = "";
				for (Contacto contacto : notificacion.getDestinatarios()) {
					if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
						codigo = this.smsService.sendSMS(empresa.getIdEmpresa(), "+502" + contacto.getTelefono(), notificacion.getNotificacion());
					} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
						this.mailService.sendMail(mailFrom, contacto.getEmail(), notificacion.getTitulo(), notificacion.getNotificacion());
					} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
					}
				}
				notificacion.setCodigo(codigo);
				notificacion.setEstado(new EstadoNotificacion(Constants.ESTADO_NOTIFICACION_ENVIADA));
				this.notificationService.save(notificacion);
			}
		}
	}
}
