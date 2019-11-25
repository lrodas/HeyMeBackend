package com.cycsystems.heymebackend;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cycsystems.heymebackend.models.entity.Contacto;
import com.cycsystems.heymebackend.models.entity.DetallePaquete;
import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.EstadoNotificacion;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;
import com.cycsystems.heymebackend.models.service.IDetallePaqueteService;
import com.cycsystems.heymebackend.models.service.IEmpresaService;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import com.cycsystems.heymebackend.models.service.IPaqueteConsumoService;
import com.cycsystems.heymebackend.models.service.IParametroService;
import com.cycsystems.heymebackend.models.service.impl.MailServiceImpl;
import com.cycsystems.heymebackend.models.service.impl.SMSServiceImpl;
import com.cycsystems.heymebackend.util.Constants;

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
	@Autowired
	private IPaqueteConsumoService paqueteConsumoService;
	@Autowired
	private IDetallePaqueteService detallePaqueteService;

	// @Scheduled(fixedDelay = 3600000)
	@Scheduled(fixedDelay = 60000)
	public void taskSendMessage() {

		List<Empresa> empresas = this.empresaService.findAll();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date fechaActual = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		Date fechaFin = calendar.getTime();
		
		for (Empresa empresa: empresas) {
			
			List<PaqueteConsumo> listaPaquetes = this.paqueteConsumoService.findPackagesByStatusAndEndDate(
					empresa.getIdEmpresa(),
					Constants.ESTADO_PAQUETE_CONSUMO_ACTIVO,
					fechaFin);
			
			if (listaPaquetes != null && listaPaquetes.size() > 0) {
				
				List<DetallePaquete> detalles = this.detallePaqueteService.findByPaquete(listaPaquetes.get(0).getPaquete().getIdPaquete());
				Integer smsRestantes = 0;
				Integer mailRestantes = 0;
				Integer whatsappRestantes = 0;
				
				for (DetallePaquete detalle: detalles) {
					if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
						smsRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoSMS();
					} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
						mailRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoMAIL();
					} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
						whatsappRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoWhatsapp();;
					}
				}
			
				List<Notificacion> notificaciones = this.notificationService.findbySendingDate(fechaActual, Constants.ESTADO_NOTIFICACION_PROGRAMADA, empresa.getIdEmpresa());
				String mailFrom = this.parametroService.findParameterByEmpresaAndName(empresa.getIdEmpresa(), Constants.REMITENTE_CORREO).getValor();
				
				for (Notificacion notificacion : notificaciones) {
					String codigo = "";
					for (Contacto contacto : notificacion.getDestinatarios()) {
						if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
							
							if (smsRestantes.compareTo(0) > 0) {								
								codigo = this.smsService.sendSMS(empresa.getIdEmpresa(), "+502" + contacto.getTelefono(), notificacion.getNotificacion());
								listaPaquetes.get(0).setConsumoSMS(listaPaquetes.get(0).getConsumoSMS() + 1);
							}
						} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
							
							if (mailRestantes.compareTo(0) > 0) {								
								this.mailService.sendMail(mailFrom, contacto.getEmail(), notificacion.getTitulo(), notificacion.getNotificacion());
							}
						
						} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
						
							if (whatsappRestantes.compareTo(0) > 0) {
								
							}
						}
					}
					notificacion.setCodigo(codigo);
					notificacion.setEstado(new EstadoNotificacion(Constants.ESTADO_NOTIFICACION_ENVIADA));
					this.notificationService.save(notificacion);
				}
				
				this.paqueteConsumoService.save(listaPaquetes.get(0));
			}
		}
	}
}
