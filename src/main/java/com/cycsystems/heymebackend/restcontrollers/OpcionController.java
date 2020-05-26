package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.convert.COpcion;
import com.cycsystems.heymebackend.input.OpcionRequest;
import com.cycsystems.heymebackend.models.entity.Opcion;
import com.cycsystems.heymebackend.models.service.impl.OpcionServiceImpl;
import com.cycsystems.heymebackend.output.OpcionResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/option")
public class OpcionController {
	
	@Autowired
	private OpcionServiceImpl opcionService;
	
	private Logger LOG = LogManager.getLogger(OpcionController.class);
	
	@Async
	@PostMapping("/findAll")
	public ListenableFuture<ResponseEntity<?>> obtenerOpciones(@RequestBody OpcionRequest input) {
		
		LOG.info("METHOD: obtenerOpciones() --PARAMS: OpcionRequest: " + input);
		OpcionResponse output = new OpcionResponse();
		
		List<Opcion> opciones = this.opcionService.findAll();
		List<com.cycsystems.heymebackend.common.Opcion> modelos = new ArrayList<>();

		output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
		output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
		output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		output.setOpciones(opciones
			.stream()
			.map(COpcion::EntityToModel)
			.collect(Collectors.toList()));
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
}
