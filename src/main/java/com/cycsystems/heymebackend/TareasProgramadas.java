package com.cycsystems.heymebackend;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.rmi.RemoteException;
import java.text.ParseException;

import javax.mail.MessagingException;

import com.cycsystems.heymebackend.common.TipoCambio;
import com.cycsystems.heymebackend.convert.CTipoCambio;
import com.cycsystems.heymebackend.models.dao.ITipoCambioDao;
import com.cycsystems.heymebackend.models.entity.*;
import com.twilio.rest.api.v2010.account.Message;

import gt.gob.banguat.www.variables.ws.TipoCambioSoapProxy;
import gt.gob.banguat.www.variables.ws.VarDolar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

	private final INotificacionService notificationService;
	private final SMSServiceImpl smsService;
	private final MailServiceImpl mailService;
	private final IEmpresaService empresaService;
	private final IParametroService parametroService;
	private final IPaqueteConsumoService paqueteConsumoService;
	private final IDetallePaqueteService detallePaqueteService;
	private final ITipoCambioDao exchangeRepo;

	private Logger LOG = LogManager.getLogger(TareasProgramadas.class);

	@Autowired
	public TareasProgramadas(INotificacionService notificationService, SMSServiceImpl smsService,
			MailServiceImpl mailService, IEmpresaService empresaService, IParametroService parametroService,
			IPaqueteConsumoService paqueteConsumoService, IDetallePaqueteService detallePaqueteService,
			ITipoCambioDao exchangeRepo) {
		this.notificationService = notificationService;
		this.smsService = smsService;
		this.mailService = mailService;
		this.empresaService = empresaService;
		this.parametroService = parametroService;
		this.paqueteConsumoService = paqueteConsumoService;
		this.detallePaqueteService = detallePaqueteService;
		this.exchangeRepo = exchangeRepo;
	}

	@Scheduled(fixedDelay = 60000)
	@Scheduled(fixedDelay = 60000)
	public void taskSendMessage() {

		List<Empresa> empresas = this.empresaService.findAll();

		Calendar calendar = Calendar.getInstance();
		Date fechaActual = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		for (Empresa empresa : empresas) {

			List<PaqueteConsumo> listaPaquetes = this.paqueteConsumoService
					.findPackagesByCompanyAndStatus(empresa.getIdEmpresa(), Constants.ESTADO_PAQUETE_CONSUMO_ACTIVO);

			if (listaPaquetes != null && listaPaquetes.size() > 0) {

				List<DetallePaquete> detalles = this.detallePaqueteService
						.findByPaquete(listaPaquetes.get(0).getPaquete().getIdPaquete());
				Integer smsRestantes = 0;
				Integer mailRestantes = 0;
				Integer whatsappRestantes = 0;

				for (DetallePaquete detalle : detalles) {
					if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {
						smsRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoSMS();
					} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {
						mailRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoMAIL();
					} else if (detalle.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {
						whatsappRestantes = detalle.getCuota() - listaPaquetes.get(0).getConsumoWhatsapp();
						;
					}
				}
				LOG.info("Fecha Actual: " + fechaActual);
				List<Notificacion> notificaciones = this.notificationService.findbySendingDate(fechaActual,
						Constants.ESTADO_NOTIFICACION_PROGRAMADA, empresa.getIdEmpresa());
				String mailFrom = this.parametroService
						.findParameterByEmpresaAndName(empresa.getIdEmpresa(), Constants.REMITENTE_CORREO).getValor();
				LOG.info("Notificaciones: " + notificaciones.size());
				for (Notificacion notificacion : notificaciones) {
					for (Contacto contacto : notificacion.getDestinatarios()) {
						if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_SMS) == 0) {

							if (smsRestantes.compareTo(0) > 0) {
								Message message = this.smsService.sendSMS(empresa.getIdEmpresa(),
										contacto.getPais().getCodigo() + contacto.getTelefono(),
										notificacion.getNotificacion());
								listaPaquetes.get(0).setConsumoSMS(listaPaquetes.get(0).getConsumoSMS() + 1);
								notificacion.setCodigo(message.getSid());
								notificacion
										.setEstado(new EstadoNotificacion(Constants.ESTADO_NOTIFICACION_ENVIADA, ""));
							}
						} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_EMAIL) == 0) {

							if (mailRestantes.compareTo(0) > 0) {
								try {
									this.mailService.sendEmailTxt(mailFrom, contacto.getEmail(),
											notificacion.getTitulo(), notificacion.getNotificacion());
								} catch (MessagingException | IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								notificacion
										.setEstado(new EstadoNotificacion(Constants.ESTADO_NOTIFICACION_ENVIADA, ""));
							}

						} else if (notificacion.getCanal().getIdCanal().compareTo(Constants.CANAL_WHATSAPP) == 0) {

							if (whatsappRestantes.compareTo(0) > 0) {
								Message message = this.smsService.sendSMS(empresa.getIdEmpresa(),
										"whatsapp:" + contacto.getPais().getCodigo() + contacto.getTelefono(),
										notificacion.getNotificacion());
								listaPaquetes.get(0).setConsumoWhatsapp(listaPaquetes.get(0).getConsumoWhatsapp() + 1);
								notificacion.setCodigo(message.getSid());
								notificacion
										.setEstado(new EstadoNotificacion(Constants.ESTADO_NOTIFICACION_ENVIADA, ""));
							}
						}
					}
					this.notificationService.save(notificacion);
				}

				this.paqueteConsumoService.save(listaPaquetes.get(0));
			}
		}
	}

	@Scheduled(cron = "0 0 0/12 1/1 * ?")
	private void activateAndDeactivatePackage() {
		List<PaqueteConsumo> paquetesInactivos = this.paqueteConsumoService
				.findPackagesByCompanyStartDateAndStatus(new Date(), Constants.ESTADO_PAQUETE_CONSUMO_INACTIVO);
		for (PaqueteConsumo paquete : paquetesInactivos) {
			paquete.setEstado(new EstadoPaqueteConsumo(Constants.ESTADO_PAQUETE_CONSUMO_ACTIVO));
			this.paqueteConsumoService.save(paquete);
		}

		List<PaqueteConsumo> paquetesActivos = this.paqueteConsumoService
				.findPackageByStatusAndEndDate(Constants.ESTADO_PAQUETE_CONSUMO_ACTIVO, new Date());
		for (PaqueteConsumo paquete : paquetesActivos) {
			paquete.setEstado(new EstadoPaqueteConsumo(Constants.ESTADO_PAQUETE_CONSUMO_VENCIDO));
			this.paqueteConsumoService.save(paquete);
		}
	}

	@Scheduled(cron = "0 0 2 * * *")
	public void taskObtainExchange() {

		TipoCambioSoapProxy dsf = new TipoCambioSoapProxy();

		try {
			VarDolar[] info = dsf.getTipoCambioSoap().tipoCambioDia().getCambioDolar();

			VarDolar d = info[0];

			TipoCambio insert = new TipoCambio();
			insert.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(d.getFecha()));
			insert.setValor(Double.parseDouble(String.valueOf(d.getReferencia())));
			insert.setEstado(true);
			this.exchangeRepo.save(CTipoCambio.ModelToEntity(insert));

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
