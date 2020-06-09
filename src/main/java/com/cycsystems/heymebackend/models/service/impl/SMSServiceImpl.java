package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.common.Notificacion;
import com.cycsystems.heymebackend.models.service.IParametroService;
import com.cycsystems.heymebackend.util.Constants;
import com.google.common.collect.Range;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.usage.Record;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SMSServiceImpl {

	private static final Logger LOG = LogManager.getLogger(SMSServiceImpl.class);
	
	@Autowired
	private IParametroService parametroService;
	
	public Message sendSMS(Integer idEmpresa, String to, String smsMessage) {
		
		String accountSid = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.ACCOUNT_SID).getValor();
		String authToken = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.AUTH_TOKEN).getValor();
		String serviceId = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.SERVICE_ID).getValor();
		
		LOG.info("USER: " + accountSid + ", PASS: " + authToken + ", to: " + to + ", Message: " + smsMessage);

		Twilio.init(accountSid, authToken);

		Message message = Message.creator(
                new PhoneNumber(to),
                serviceId,
                smsMessage)
            .create();

		LOG.info("Mensaje: " + message.getStatus());
		return message;
	}

	public String sendWhatsapp(Integer idEmpresa, String to, String txtMessage) {
		String accountSid = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.ACCOUNT_SID).getValor();
		String authToken = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.AUTH_TOKEN).getValor();
		// String serviceId = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.SERVICE_ID).getValor();

		LOG.info("USER: " + accountSid + ", PASS: " + authToken);

		Twilio.init(accountSid, authToken);

		Message message = Message.creator(
				new PhoneNumber("whatsapp:+17033489896"),
				new PhoneNumber("whatsapp:" + to),
				txtMessage)
				.create();

		LOG.info("Mensaje: " + message.getStatus());
		return message.getSid();
	}

	public List<Message> retrieveSMSByIdList(List<String> ssids, Integer idEmpresa) {

		List<Message> messages = new ArrayList<>();
		String accountSid = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.ACCOUNT_SID).getValor();
		String authToken = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.AUTH_TOKEN).getValor();
		Twilio.init(accountSid, authToken);

		for (String ssid: ssids) {
			messages.add(Message.fetcher(ssid).fetch());
		}

		return messages;
	}

	public ResourceSet<Message> retrieveSMSByRangeDate(Integer idEmpresa, Date fechaInicio, Date fechaFin) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String accountSid = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.ACCOUNT_SID).getValor();
		String authToken = this.parametroService.findParameterByEmpresaAndName(idEmpresa, Constants.AUTH_TOKEN).getValor();
		Twilio.init(accountSid, authToken);
		return Message.reader()
				.setDateSent(Range.open(DateTime.parse(sdf.format(fechaInicio)), DateTime.parse(sdf.format(fechaFin))))
				.read();
	}

	public static void main(String[] args) {
		Twilio.init("AC32599858daab272963667e14e797b929", "1260ba2b54f7fe5fd27ba5a6a816a5d7");


		Message message = Message.creator(
				new PhoneNumber("whatsapp:+50255742084"),
				"MGca0f5a07c33c3663c04e712b687b1d80",
				"Su código c y c systems guatemala es 12345")
				.create();
		System.out.println(message.getStatus().toString());
	}
}
