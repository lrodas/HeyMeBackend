package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.input.MailRequest;
import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.service.IEmpresaService;
import com.cycsystems.heymebackend.models.service.impl.MailServiceImpl;
import com.cycsystems.heymebackend.output.MailResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/mail")
public class MailController {

	private final Logger LOG = LogManager.getLogger(MailController.class);

	@Value("${template.welcome}")
	private String templateWelcome;

	private final MailServiceImpl mailService;

	private final IEmpresaService empresaService;

	@Autowired
	public MailController(MailServiceImpl mailService, IEmpresaService empresaService) {
		this.mailService = mailService;
		this.empresaService = empresaService;
	}

	@Async
	@PostMapping("/send")
	public ListenableFuture<ResponseEntity<MailResponse>> enviarMail(@RequestBody MailRequest request) {
		LOG.info("METHOD: enviarMail() --PARAMS: " + request);
		MailResponse response = new MailResponse();

		if (request.getDestinatario() == null || request.getDestinatario().isEmpty()) {
			response.setCodigo(Response.NOTIFICATION_CONTACTS_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.NOTIFICATION_CONTACTS_NOT_EMPTY.getMessage());
			response.setIndicador(Response.NOTIFICATION_CONTACTS_NOT_EMPTY.getIndicador());
		} else if (request.getAsunto() == null || request.getAsunto().isEmpty()) {
			response.setCodigo(Response.NOTIFICATION_TITLE_ERROR.getCodigo());
			response.setDescripcion(Response.NOTIFICATION_TITLE_ERROR.getMessage());
			response.setIndicador(Response.NOTIFICATION_TITLE_ERROR.getIndicador());
		} else if (request.getMensaje() == null || request.getMensaje().isEmpty()) {
			response.setCodigo(Response.NOTIFICATION_CONTENT_ERROR.getCodigo());
			response.setDescripcion(Response.NOTIFICATION_CONTENT_ERROR.getMessage());
			response.setIndicador(Response.NOTIFICATION_CONTENT_ERROR.getIndicador());
		} else if (request.getRemitente() == null || request.getRemitente().isEmpty()) {
			response.setCodigo(Response.NOTIFICATION_SENDER_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.NOTIFICATION_SENDER_NOT_EMPTY.getMessage());
			response.setIndicador(Response.NOTIFICATION_SENDER_NOT_EMPTY.getIndicador());
		} else if (request.getCodigoEmpresa() == null || request.getCodigoEmpresa().isEmpty()) {
			response.setCodigo(Response.COMPANY_CODE_NOT_EMPTY.getCodigo());
			response.setDescripcion(Response.COMPANY_CODE_NOT_EMPTY.getMessage());
			response.setIndicador(Response.COMPANY_CODE_NOT_EMPTY.getIndicador());
		} else {
			Empresa empresa = this.empresaService.findByCode(request.getCodigoEmpresa());

			if (empresa == null) {
				response.setCodigo(Response.COMPANY_NOT_EXIST.getCodigo());
				response.setDescripcion(Response.COMPANY_NOT_EXIST.getMessage());
				response.setIndicador(Response.COMPANY_NOT_EXIST.getIndicador());
			} else {
//				this.mailService.sendMail(request.getRemitente(), request.getDestinatario(), request.getAsunto(),
//						request.getMensaje());

				try {
					this.mailService.sendEmail(request.getRemitente(), request.getDestinatario(), request.getAsunto(),
							"www.easystore.com.gt", templateWelcome);
				} catch (MessagingException | IOException e) {
					e.printStackTrace();

					System.out.println("error al enviar correo: " + e.getMessage());
				}

				response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
				response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
				response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(response));
	}

}
