package com.cycsystems.heymebackend.reportControllers;

import com.cycsystems.heymebackend.input.NotificacionRequest;
import com.cycsystems.heymebackend.models.entity.Notificacion;
import com.cycsystems.heymebackend.models.service.INotificacionService;
import com.cycsystems.heymebackend.output.NotificacionResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.GeneradorExcel;
import com.cycsystems.heymebackend.util.GeneradorPdf;
import com.itextpdf.text.DocumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/report")
public class ReportNotificationController {
	
	private Logger LOG = LogManager.getLogger(ReportNotificationController.class);
	
	@Autowired
	private INotificacionService notificacionService;
	
	@Async
	@PostMapping("/notificationReportExcel")
	public ListenableFuture<ResponseEntity<?>> notificationReportExcel(@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: notificationReportExcel() --PARAMS: notificacionesRequest: " + input);
		List<Notificacion> notificaciones = new ArrayList<>();
		NotificacionResponse output = new NotificacionResponse();
		
//		output.setCodigo("0060");
//		output.setDescripcion("Debe enviar el estado de la notificacion");
//		output.setIndicador("ERROR");
//		return new AsyncResult<>(ResponseEntity.ok(output));
		
		Calendar calendar = Calendar.getInstance();
		
		Integer tipo = input.getTipo();
		if (tipo == Constants.NOTIFICACION_TIPO_BUSQUEDA_ESTADO) {
		} else if (tipo == Constants.NOTIFICACION_TIPO_BUSQUEDA_TITULO) {
			notificaciones = this.notificacionService.findByTitle(input.getNotificacion().getTitulo(), input.getNotificacion().getEstado().getIdEstadoNotificacion());
		} else if (tipo == Constants.NOTIFICACION_TIPO_BUSQUEDA_USUARIO) {
			notificaciones = this.notificacionService.findByUser(input.getNombreUsuario(), input.getNotificacion().getEstado().getIdEstadoNotificacion());
		} else if (tipo == Constants.NOTIFICACION_TIPO_BUSQUEDA_FECHA_PROGRAMACION) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getYear(),
					LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getMonthValue() - 1,
			LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getDayOfMonth() + 1);
			Date fechaInicioProgramacion = calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getYear(),
					LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getMonthValue() - 1,
			LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getDayOfMonth() + 1);
			Date fechaFinProgramacion = calendar.getTime();
			notificaciones = this.notificacionService.findByProgrammingDate(fechaInicioProgramacion, fechaFinProgramacion);
		} else if (tipo == Constants.NOTIFICACION_TIPO_BUSQUEDA_FECHA_ENVIO) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getYear(),
					LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getMonthValue() - 1,
			LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getDayOfMonth() + 1);
			Date fechaInicioEnvio = calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getYear(),
					LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getMonthValue() - 1,
			LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getDayOfMonth() + 1);
			Date fechaFinEnvio = calendar.getTime();
			notificaciones = this.notificacionService.findByShippingDate(fechaInicioEnvio, fechaFinEnvio);
		}
		
		LOG.info("notificaciones: " + notificaciones);
		
		ByteArrayInputStream bis = null;
		try {
			bis = GeneradorExcel.reporteNotificaciones(notificaciones);

		} catch (IOException e) {
			LOG.info("Ocurrio un error al generar el reporte por el excel");
		}

		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=notificaciones.xlsx");

        return new AsyncResult<>(ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(bis)));
	}

	@Async
	@PostMapping("/notificationReportPdf")
	public ListenableFuture<ResponseEntity<?>> notificationReport(@RequestBody NotificacionRequest input) {
		
		LOG.info("METHOD: notificationReport() --PARAMS: notificacionesRequest: " + input);
		List<Notificacion> notificaciones = new ArrayList<>();
		NotificacionResponse output = new NotificacionResponse();
		
		if (input.getNotificacion() == null ||
				input.getNotificacion().getEstado() == null || 
				input.getNotificacion().getEstado().getIdEstadoNotificacion() == null ||
				input.getNotificacion().getEstado().getIdEstadoNotificacion() <= 0) {
			
			output.setCodigo("0060");
			output.setDescripcion("Debe enviar el estado de la notificacion");
			output.setIndicador("ERROR");
			return new AsyncResult<>(ResponseEntity.ok(output));
			
		} else if (input.getNotificacion().getTitulo() != null && 
				!input.getNotificacion().getTitulo().isEmpty()) {
			
			notificaciones = this.notificacionService.findByTitle(input.getNotificacion().getTitulo(), input.getNotificacion().getEstado().getIdEstadoNotificacion());
		
		} else if (input.getNombreUsuario() != null && !input.getNombreUsuario().isEmpty()) {
			
			notificaciones = this.notificacionService.findByUser(input.getNombreUsuario(), input.getNotificacion().getEstado().getIdEstadoNotificacion());
		
		} else if (input != null &&
				input.getFechaFin() != null && input.getFechaInicio() != null) {
			
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);
		    
		    calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getYear(),
		    		LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getMonthValue() - 1,
		    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaInicio())).getDayOfMonth() + 1);
		    
		    Date fechaInicio = calendar.getTime();
		    
		    calendar.set(Calendar.HOUR_OF_DAY, 23);
		    calendar.set(Calendar.MINUTE, 59);
		    calendar.set(Calendar.SECOND, 59);
		    calendar.set(Calendar.MILLISECOND, 0);
		    
		    calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getYear(),
		    		LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getMonthValue() - 1,
		    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(input.getFechaFin())).getDayOfMonth() + 1);
		    
		    Date fechaFin = calendar.getTime();
			
			notificaciones = this.notificacionService.findByProgrammingDate(fechaInicio, fechaFin);

		}
		
		LOG.info("notificaciones: " + notificaciones);
		
		ByteArrayInputStream bis = null;
		try {
			bis = GeneradorPdf.ReporteNotificaciones(notificaciones);
		} catch (DocumentException e) {
			LOG.info("Ocurrio un error al generar el reporte por el pdfWritter");
		}

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "inline; filename=reporteNotificaciones.pdf");

        return new AsyncResult<>(ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis)));
	}
}
