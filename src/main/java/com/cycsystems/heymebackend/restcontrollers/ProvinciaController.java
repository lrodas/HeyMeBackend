package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.Pais;
import com.cycsystems.heymebackend.common.Region;
import com.cycsystems.heymebackend.input.ProvinciaRequest;
import com.cycsystems.heymebackend.models.entity.Provincia;
import com.cycsystems.heymebackend.models.service.impl.ProvinciaServiceImpl;
import com.cycsystems.heymebackend.output.ProvinciaResponse;
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
@RequestMapping("/api/" + Constants.VERSION + "/province")
public class ProvinciaController {

	private Logger LOG = LogManager.getLogger(RegionController.class);
	
	@Autowired
	private ProvinciaServiceImpl provinciaService;
	
	@Async
	@PostMapping("/retrieveProvince")
	public ListenableFuture<ResponseEntity<?>> obtenerProvincias(@RequestBody ProvinciaRequest input) {
		
		LOG.info("METHOD: obtenerProvincias() --PARAMS: provinciaRequest: " + input);
		ProvinciaResponse output = new ProvinciaResponse();
		List<Provincia> provincias = this.provinciaService.findAll();
		
		output.setCodigo("0000");
		output.setIndicador("SUCCESS");
		output.setDescripcion("Provincias obtenidas exitosamente");
		output.setProvincias(this.mapProvincias(provincias));
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	@Async
	@PostMapping("/retrieveProvinceByRegion")
	public ListenableFuture<ResponseEntity<?>> obtenerProvinciaPorRegion(@RequestBody ProvinciaRequest input) {
		
		LOG.info("METHOD: obtenerProvinciaPorRegion() --PARAMS: provinciaRequest: " + input);
		
		ProvinciaResponse output = new ProvinciaResponse();
		
		if (input.getProvincia() == null || 
				input.getProvincia().getRegion() == null || 
				input.getProvincia().getRegion().getIdRegion() == null ||
				input.getProvincia().getRegion().getIdRegion() <= 0) {
			output.setCodigo("0057");
			output.setIndicador("ERROR");
			output.setDescripcion("Debe enviar el id de region para realizar la busqueda");	
		} else {
			
			List<Provincia> provincias = this.provinciaService.findByRegion(input.getProvincia().getRegion().getIdRegion());
			
			output.setCodigo("0000");
			output.setIndicador("SUCCESS");
			output.setDescripcion("Provincias obtenidas exitosamente");
			output.setProvincias(this.mapProvincias(provincias));
		}
		
		return new AsyncResult<>(ResponseEntity.ok(output));
	}
	
	private List<com.cycsystems.heymebackend.common.Provincia> mapProvincias(List<Provincia> provincias) {
		
		List<com.cycsystems.heymebackend.common.Provincia> modelos = new ArrayList<>();
		
		for (Provincia provincia: provincias) {
			com.cycsystems.heymebackend.common.Provincia modelo = new com.cycsystems.heymebackend.common.Provincia();
			modelo.setIdProvincia(provincia.getIdProvincia());
			modelo.setNombre(provincia.getNombre());
			modelo.setRegion(new Region(
					provincia.getRegion().getIdRegion(),
					provincia.getRegion().getNombre(),
					new Pais(
							provincia.getRegion().getPais().getIdPais(),
							provincia.getRegion().getPais().getNombre(),
							provincia.getRegion().getPais().getCodigo(),
							provincia.getRegion().getPais().getEstado()
							)
					));
			modelos.add(modelo);
		}
		
		return modelos;
	}
}
