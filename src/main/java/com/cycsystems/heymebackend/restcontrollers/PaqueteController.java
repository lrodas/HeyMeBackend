package com.cycsystems.heymebackend.restcontrollers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cycsystems.heymebackend.convert.CConsumo;
import com.cycsystems.heymebackend.convert.CDetallePaquete;
import com.cycsystems.heymebackend.convert.CPaquete;
import com.cycsystems.heymebackend.models.service.IDetallePaqueteService;
import com.cycsystems.heymebackend.util.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	private final IPaqueteConsumoService consumoService;
	private final IPaqueteService paqueteService;
	private final IUsuarioService usuarioService;
	private final IDetallePaqueteService detallePaqueteService;

	@Autowired
	public PaqueteController(IPaqueteConsumoService consumoService, IPaqueteService paqueteService,
			IUsuarioService usuarioService, IDetallePaqueteService detallePaqueteService) {
		this.consumoService = consumoService;
		this.paqueteService = paqueteService;
		this.usuarioService = usuarioService;
		this.detallePaqueteService = detallePaqueteService;
	}

	@Async
	@PostMapping("/saveConsumption")
	public ListenableFuture<ResponseEntity<PaqueteResponse>> guardarPaquete(@RequestBody PaqueteRequest input) {
		LOG.info("METHOD: guardarPaquete() --PARAMS: PaqueteResponse: " + input);
		PaqueteResponse output = new PaqueteResponse();

		if (input.getPaquete() == null) {
			output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
		} else if (input.getPaquete().getIdPaquete() == null || input.getPaquete().getIdPaquete() <= 0) {
			output.setCodigo(Response.PACKAGE_NOT_AVAILABE.getCodigo());
			output.setDescripcion(Response.PACKAGE_NOT_EMPTY.getMessage());
			output.setIndicador(Response.PACKAGE_NOT_EMPTY.getIndicador());
		} else if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
			output.setCodigo(Response.USER_NOT_EMPTY.getCodigo());
			output.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
			output.setIndicador(Response.USER_NOT_EMPTY.getIndicador());
		} else if (input.getJsonResponse() == null || input.getJsonResponse().isEmpty()) {
			output.setCodigo(Response.JSON_RESPONSE_EMPTY.getCodigo());
			output.setDescripcion(Response.JSON_RESPONSE_EMPTY.getMessage());
			output.setIndicador(Response.JSON_RESPONSE_EMPTY.getIndicador());
		} else {

			Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

			if (usuario == null) {
				output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
				output.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
				output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
			} else {
				Calendar calendar = Calendar.getInstance();
				Paquete paquete = this.paqueteService.findById(input.getPaquete().getIdPaquete());

				if (paquete == null) {
					output.setCodigo(Response.PACKAGE_NOT_EXIST.getCodigo());
					output.setDescripcion(Response.PACKAGE_NOT_EXIST.getMessage());
					output.setIndicador(Response.PACKAGE_NOT_EXIST.getIndicador());
				} else {

					try {
						JSONObject jsonObject = new JSONObject(input.getJsonResponse());

						if (jsonObject.getString("status").equalsIgnoreCase("COMPLETED")) {

							PaqueteConsumo entity = new PaqueteConsumo();
							Date fechaCompra = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
									.parse(jsonObject.getString("create_time"));

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
							entity.setResponsePasarela(input.getJsonResponse());
							entity.setFechaCompra(fechaCompra);

							entity = this.consumoService.save(entity);

							output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
							output.setConsumo(CConsumo.EntityToModel(entity));
							output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
							output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
						} else {
							output.setCodigo(Response.COBRO_NO_REALIZADO_ERROR.getCodigo());
							output.setDescripcion(Response.COBRO_NO_REALIZADO_ERROR.getMessage());
							output.setIndicador(Response.COBRO_NO_REALIZADO_ERROR.getIndicador());
						}
					} catch (JSONException e) {
						output.setCodigo(Response.JSON_RESPONSE_NOT_VALID.getCodigo());
						output.setDescripcion(Response.JSON_RESPONSE_NOT_VALID.getMessage());
						output.setIndicador(Response.JSON_RESPONSE_NOT_VALID.getIndicador());
					} catch (ParseException e) {
						output.setCodigo(Response.DATE_FORMAT_NOT_VALID.getCodigo());
						output.setDescripcion(Response.DATE_FORMAT_NOT_VALID.getMessage());
						output.setIndicador(Response.DATE_FORMAT_NOT_VALID.getIndicador());
					}
				}
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(output));
	}

	@Async
	@PostMapping("/retrieveAvaiblePackage")
	public ListenableFuture<ResponseEntity<PaqueteResponse>> obtenerPaquetesDisponibles(
			@RequestBody PaqueteRequest request) {
		LOG.info("METHOD: obtenerPaquetesDisponibles() --PARAMS: " + request);
		PaqueteResponse response = new PaqueteResponse();

		List<Paquete> paquetes = this.paqueteService.findByStatusPackage(Constants.ESTADO_PAQUETE_ACTIVO);

		response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
		response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
		response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());

		if (paquetes != null && !paquetes.isEmpty()) {
			response.setPaquetes(paquetes.stream().map(paquete -> {
				com.cycsystems.heymebackend.common.Paquete model = new com.cycsystems.heymebackend.common.Paquete();
				model = CPaquete.EntityToModel(paquete);
				model.setDetalle(this.detallePaqueteService.findByPaquete(paquete.getIdPaquete()).stream()
						.map(CDetallePaquete::EntityToModel).collect(Collectors.toList()));
				return model;
			}).collect(Collectors.toList()));
		}

		return new AsyncResult<>(ResponseEntity.ok(response));
	}
}
