package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.input.OpcionRequest;
import com.cycsystems.heymebackend.models.entity.Opcion;
import com.cycsystems.heymebackend.models.service.impl.OpcionServiceImpl;
import com.cycsystems.heymebackend.output.OpcionResponse;
import com.cycsystems.heymebackend.util.Constants;
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
		LOG.info(opciones);
		for (Opcion opcion: opciones) {
			modelos.add(this.mapearOpcion(opcion));
		}
		output.setCodigo("0000");
		output.setDescripcion("Opciones obtenidos exitosamente");
		output.setIndicador("SUCCESS");
		output.setOpciones(modelos);
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	private com.cycsystems.heymebackend.common.Opcion mapearOpcion(Opcion opcion) {
		
		com.cycsystems.heymebackend.common.Opcion modelo = new com.cycsystems.heymebackend.common.Opcion();
		modelo.setIdOpcion(opcion.getIdOpcion());
		modelo.setDescripcion(opcion.getDescripcion());
		modelo.setEvento(opcion.isEvento());
		modelo.setIcono(opcion.getIcono());
		modelo.setOrden(opcion.getOrden());
		modelo.setUrl(opcion.getUrl());
		return modelo;
	}

}
