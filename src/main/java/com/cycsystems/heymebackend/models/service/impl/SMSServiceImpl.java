package com.cycsystems.heymebackend.models.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl {

	private static final Logger LOG = LogManager.getLogger(SMSServiceImpl.class);
	
	@Value("${twilio.account.service.id}")
	private String SERVICE_ID;

	@Value("${twilio.account.auth.token}")
	private String AUTH_TOKEN;

	@Value("${twilio.account.sid}")
	private String ACCOUNT_SID;
	
	public String sendSMS(String to, String smsMessage) {
		LOG.info("USER: " + ACCOUNT_SID + ", PASS" + AUTH_TOKEN);

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		Message message = Message.creator( 
                new PhoneNumber(to),  
                SERVICE_ID,
                smsMessage)      
            .create();
		
		LOG.info("Mensaje: " + message.getStatus());
		return message.getSid();
	}
/*
	public static void main(String[] args) {
		Twilio.init("AC32599858daab272963667e14e797b929", "1260ba2b54f7fe5fd27ba5a6a816a5d7");

		Message message = Message.creator(
				new PhoneNumber("+50255742084"),
				"MGca0f5a07c33c3663c04e712b687b1d80",
				"ESTO ES otra GRAN PRUEBA")
				.create();
		System.out.println(message.getStatus().toString());
	}*/
}
