package com.cycsystems.heymebackend.restcontrollers;

import java.util.Calendar;

import com.cycsystems.heymebackend.util.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cycsystems.heymebackend.common.Consumo;
import com.cycsystems.heymebackend.common.Empresa;
import com.cycsystems.heymebackend.input.PaqueteRequest;
import com.cycsystems.heymebackend.models.entity.EstadoPaqueteConsumo;
import com.cycsystems.heymebackend.models.entity.Paquete;
import com.cycsystems.heymebackend.models.entity.PaqueteConsumo;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IPaqueteConsumoService;
import com.cycsystems.heymebackend.models.service.IPaqueteService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.PaqueteResponse;
import com.cycsystems.heymebackend.util.Constants;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/package")
public class PaqueteController {

	
	private Logger LOG = LogManager.getLogger(PaqueteController.class);
	
	@Autowired
	private IPaqueteConsumoService consumoService;
	@Autowired
	private IPaqueteService paqueteService;
	@Autowired
	private IUsuarioService usuarioService;
	
	@PostMapping("/saveConsumption")
	public ListenableFuture<ResponseEntity<PaqueteResponse>> guardarPaquete(
			@RequestBody PaqueteRequest input) {
		LOG.info("METHOD: guardarPaquete() --PARAMS: PaqueteResponse: " + input);
		PaqueteResponse output = new PaqueteResponse();
		
		if (input.getPaquete() == null) {
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		} else if (input.getPaquete().getIdPaquete() == null ||
				input.getPaquete().getIdPaquete() <= 0) {
			output.setCodigo(Response.PACKAGE_NOT_AVAILABE.getCodigo());
			output.setDescripcion(Response.PACKAGE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.PACKAGE_NOT_EMPTY.getIndicador());
		} else if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			output.setCodigo(Response.USER_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
			output.setIndicador(Response.USER_NOT_EMPTY.getIndicador());
		} else {
			
			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());
			PaqueteConsumo entity = new PaqueteConsumo();
			Calendar calendar = Calendar.getInstance();
			Paquete paquete = this.paqueteService.findById(input.getPaquete().getIdPaquete());
			
			entity.setConsumoMAIL(0);
			entity.setConsumoSMS(0);
			entity.setConsumoWhatsapp(0);
			entity.setEmpresa(usuario.getEmpresa());
			entity.setEstado(new EstadoPaqueteConsumo(Constants.ESTADO_PAQUETE_CONSUMO_ACTIVO));
			calendar.set(Calendar.DATE, 1);
			entity.setFechaInicio(calendar.getTime());
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)); 
			entity.setFechaFin(calendar.getTime());
			entity.setPaquete(paquete);
			
			entity = this.consumoService.save(entity);
			
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setConsumo(this.mapModel(entity));
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	private Consumo mapModel(PaqueteConsumo entity) {
		Consumo model = new Consumo();
		model.setConsumoEmail(entity.getConsumoMAIL());
		model.setConsumoSMS(entity.getConsumoSMS());
		model.setConsumoWhatsapp(entity.getConsumoWhatsapp());
		model.setEmpresa(
				new Empresa(
						entity.getEmpresa().getIdEmpresa(),
						entity.getEmpresa().getNombreEmpresa(),
						entity.getEmpresa().getDireccion(),
						entity.getEmpresa().getTelefono()));
		model.setFechaFin(entity.getFechaFin());
		model.setFechaInicio(entity.getFechaInicio());
		model.setIdPaqueteConsumo(entity.getIdPaqueteConsumo());
		model.setPaquete(
				new com.cycsystems.heymebackend.common.Paquete(
						entity.getPaquete().getIdPaquete(),
						entity.getPaquete().getNombre(),
						entity.getPaquete().getPrecio()));
		return model;
	}
}
