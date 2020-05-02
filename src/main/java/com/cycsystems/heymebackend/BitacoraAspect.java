package com.cycsystems.heymebackend;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.models.entity.Bitacora;
import com.cycsystems.heymebackend.models.entity.TipoOperacion;
import com.cycsystems.heymebackend.models.service.IBitacoraService;
import com.cycsystems.heymebackend.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class BitacoraAspect {

	private static Logger LOG = LogManager.getLogger(BitacoraAspect.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private final IBitacoraService bitacoraService;

	@Autowired
	public BitacoraAspect(IBitacoraService bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

	@Before("within(com.cycsystems.heymebackend.restcontrollers..*)")
	public void endpointBefore(JoinPoint p) {
		
		String metodo = p.getSignature().getName();
		LOG.info(p.getTarget().getClass().getSimpleName() + ", " + metodo + " START");
		Object[] signatureArgs = p.getArgs();
		
		if (!metodo.equalsIgnoreCase("obtenerImagen")) {
			try {
				
				if (signatureArgs[0] != null) {
					
					Bitacora bitacora = new Bitacora();
					bitacora.setMetodo(metodo);
					bitacora.setTipoOperacion(new TipoOperacion(Constants.REQUEST_IN, ""));
					bitacora.setJson(objectMapper.writeValueAsString(signatureArgs[0]));
					BaseInput base = (BaseInput) signatureArgs[0];
					bitacora.setPagina(base.getPagina());
					bitacora.setError("");
					
					this.bitacoraService.save(bitacora);
					LOG.info("Request object: " + objectMapper.writeValueAsString(signatureArgs[0]));
				}
			} catch (Exception e) {
				LOG.info("Error en enpointBefore(): " + e);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@AfterReturning(value = ("within(com.cycsystems.heymebackend.restcontrollers..*)"), returning = "returnValue")
	public void endpointAfterReturning(JoinPoint p, Object returnValue) {
		ListenableFuture<ResponseEntity<?>> value = (ListenableFuture<ResponseEntity<?>>) returnValue;
		value.addCallback(result -> {
			String metodo = p.getSignature().getName();			
			if (!metodo.equalsIgnoreCase("obtenerImagen")) {
				try {
					Object[] signatureArgs = p.getArgs();
					if (signatureArgs.length > 0 && signatureArgs[0] != null) {
						Bitacora bitacora = new Bitacora();
						bitacora.setMetodo(metodo);
						assert result != null;
						bitacora.setJson(objectMapper.writer().writeValueAsString(result.getBody()));
						bitacora.setTipoOperacion(new TipoOperacion(Constants.REPLAY_OUT, ""));
						bitacora.setPagina("");
						bitacora.setError("");
						this.bitacoraService.save(bitacora);
						LOG.info("Response object: " + bitacora.getJson());
					}
				} catch (Exception e) {
					LOG.error("Error en endpointAfterReturning", e);
				}
			}
			LOG.info(p.getTarget().getClass().getSimpleName() + " " + metodo + " END");
		}, error -> LOG.error("Error en endpointAfterReturning", error));
	}
	
	@AfterThrowing(value = ("within(com.cycsystems.heymebackend.restcontrollers..*)"), throwing = "e")
	public void endpointAfterThrowing(JoinPoint p, Throwable e) {
		try {
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			LOG.info("METHOD: " + e.getClass().getSimpleName());
			LOG.info("ERROR: " + e.getMessage());
			Object[] signatureArgs = p.getArgs();
			if (!e.getClass().getSimpleName().equalsIgnoreCase("obtenerImagen") &&
					!e.getClass().getSimpleName().equalsIgnoreCase("NullPointerException")) {
				BaseInput input = (BaseInput) signatureArgs[0];
				Bitacora bitacora = new Bitacora();
				bitacora.setJson(objectMapper.writer().writeValueAsString(input));
				bitacora.setMetodo(e.getClass().getSimpleName());
				bitacora.setPagina(request.getRequestURI());
				bitacora.setError(e.getMessage() + "\n" + e.getCause());
				bitacora.setTipoOperacion(new TipoOperacion(Constants.ERROR_OUT, ""));
				this.bitacoraService.save(bitacora);
			}
			LOG.info("Error: " + e);
		}
		catch (Exception e1)
		{
			LOG.error("Error en endpointAfterThrowing", e1);
		}
	}
}
