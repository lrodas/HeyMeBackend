package com.cycsystems.heymebackend.restcontrollers;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cycsystems.heymebackend.convert.CTipoCambio;
import com.cycsystems.heymebackend.input.TipoCambioRequest;
import com.cycsystems.heymebackend.models.dao.ITipoCambioDao;
import com.cycsystems.heymebackend.models.entity.TipoCambio;
import com.cycsystems.heymebackend.output.TipoCambioResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;

import gt.gob.banguat.www.variables.ws.TipoCambioSoapProxy;
import gt.gob.banguat.www.variables.ws.VarDolar;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/exchange")
public class TipoCambioController {

	private Logger LOG = LogManager.getLogger(TipoCambioController.class);

	private ITipoCambioDao exchangeRepo;

	@Autowired
	public TipoCambioController(ITipoCambioDao exchangeRepo) {
		this.exchangeRepo = exchangeRepo;
	}

	@Async
	@PostMapping("/updateExchange")
	public ListenableFuture<ResponseEntity<TipoCambioResponse>> updateExchange(TipoCambioRequest request) {
		LOG.info("METHOD: updateExchange() --PARAMS: " + request);
		TipoCambioResponse response = new TipoCambioResponse();

		TipoCambioSoapProxy dsf = new TipoCambioSoapProxy();

		try {
			VarDolar[] info = dsf.getTipoCambioSoap().tipoCambioDia().getCambioDolar();

			System.out.println("infro: " + info);

			VarDolar d = info[0];

			System.out.println("d: " + d);

			com.cycsystems.heymebackend.common.TipoCambio insert = new com.cycsystems.heymebackend.common.TipoCambio();
			insert.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(d.getFecha()));
			insert.setValor(Double.parseDouble(String.valueOf(d.getReferencia())));
			insert.setEstado(true);
			this.exchangeRepo.save(CTipoCambio.ModelToEntity(insert));

			response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
			response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
			response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());

		} catch (RemoteException e) {
			e.printStackTrace();
			response.setCodigo(Response.ERROR_TRY.getCodigo());
			response.setDescripcion(Response.ERROR_TRY.getMessage());
			response.setIndicador(Response.ERROR_TRY.getIndicador());
		} catch (ParseException e) {
			e.printStackTrace();
			response.setCodigo(Response.ERROR_TRY.getCodigo());
			response.setDescripcion(Response.ERROR_TRY.getMessage());
			response.setIndicador(Response.ERROR_TRY.getIndicador());
		}

		return new AsyncResult<>(ResponseEntity.ok(response));
	}

	@Async
	@PostMapping("/retriveExchange")
	public ListenableFuture<ResponseEntity<TipoCambioResponse>> obtainExchange() {
		LOG.info("METHOD: obtainExchange() --PARAMS: ");
		TipoCambioResponse response = new TipoCambioResponse();

		try {

			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);

			TipoCambio exchange = this.exchangeRepo
					.findByFecha(new SimpleDateFormat("yyyy-MM-dd").parse(date.toString()));

			System.out.println("dsfsdfsdfsdf " + exchange);

			if (exchange == null) {

				TipoCambioSoapProxy dsf = new TipoCambioSoapProxy();

				try {
					VarDolar[] info = dsf.getTipoCambioSoap().tipoCambioDia().getCambioDolar();

					VarDolar d = info[0];

					com.cycsystems.heymebackend.common.TipoCambio insert = new com.cycsystems.heymebackend.common.TipoCambio();
					insert.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(d.getFecha()));
					insert.setValor(Double.parseDouble(String.valueOf(d.getReferencia())));
					insert.setEstado(true);
					this.exchangeRepo.save(CTipoCambio.ModelToEntity(insert));

					response.setTipoCambio(insert);
					response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
					response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
					response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());

				} catch (RemoteException e) {
					e.printStackTrace();
					response.setCodigo(Response.ERROR_TRY.getCodigo());
					response.setDescripcion(Response.ERROR_TRY.getMessage());
					response.setIndicador(Response.ERROR_TRY.getIndicador());
				} catch (ParseException e) {
					e.printStackTrace();
					response.setCodigo(Response.ERROR_TRY.getCodigo());
					response.setDescripcion(Response.ERROR_TRY.getMessage());
					response.setIndicador(Response.ERROR_TRY.getIndicador());
				}
			} else {
				com.cycsystems.heymebackend.common.TipoCambio exchangeResponse = CTipoCambio.EntityToModel(exchange);

				response.setTipoCambio(exchangeResponse);
				response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}

		} catch (Exception e) {
			LOG.info("METHOD: obtainExchange() --ERROR TRY: " + e.getMessage());
			response.setCodigo(Response.ERROR_TRY.getCodigo());
			response.setDescripcion(Response.ERROR_TRY.getMessage());
			response.setIndicador(Response.ERROR_TRY.getIndicador());
		}

		return new AsyncResult<>(ResponseEntity.ok(response));
	}

}
