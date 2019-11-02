package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.Pais;
import com.cycsystems.heymebackend.input.RegionRequest;
import com.cycsystems.heymebackend.models.entity.Region;
import com.cycsystems.heymebackend.models.service.IRegionService;
import com.cycsystems.heymebackend.output.RegionResponse;
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
@RequestMapping("/api/" + Constants.VERSION + "/region")
public class RegionController {
	
	private Logger LOG = LogManager.getLogger(RegionController.class);
	
	@Autowired
	private IRegionService regionService;
	
	@Async
	@PostMapping("/retrieveRegion")
	public ListenableFuture<ResponseEntity<?>> obtenerRegiones(@RequestBody RegionRequest input) {
		
		LOG.info("METHOD: obtenerRegiones() --PARAMS: regionRequest: " + input);
		List<com.cycsystems.heymebackend.common.Region> modelos = new ArrayList<>();
		List<Region> regiones = this.regionService.findAll();
		RegionResponse output = new RegionResponse();
		
		for (Region region: regiones) {
			com.cycsystems.heymebackend.common.Region modelo = new com.cycsystems.heymebackend.common.Region();
			modelo.setIdRegion(region.getIdRegion());
			modelo.setNombre(region.getNombre());
			modelo.setPais(new Pais(region.getPais().getIdPais(), region.getPais().getNombre()));
			modelos.add(modelo);
		}
		
		if (regiones == null || regiones.isEmpty()) {
			output.setCodigo("0056");
			output.setIndicador("ERROR");
			output.setDescripcion("No hay regiones disponibles, por favor intente mas tarde");
		} else {			
			output.setCodigo("0000");
			output.setIndicador("SUCCESS");
			output.setDescripcion("Regiones obtenidas exitosamente");
			output.setRegiones(modelos);
		}
		
		return new AsyncResult<ResponseEntity<?>>(ResponseEntity.ok(output));
	}

}
