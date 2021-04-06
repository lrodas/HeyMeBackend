package com.cycsystems.heymebackend.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class MailServiceImpl {

//	@Autowired
//	private JavaMailSender sender;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

//	public void sendMail(String from, String to, String subject, String body) {
//
//		SimpleMailMessage mail = new SimpleMailMessage();
//
//		MimeMessagePreparator preparator = new MimeMessagePreparator() {
//			@Override
//			public void prepare(MimeMessage mimeMessage) throws Exception {
//				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//				message.setTo(to);
//				message.setSubject(subject);
//				message.setSentDate(new Date());
//				message.setFrom(from);
//				message.setText(body, true);
//			}
//		};
//		this.mailSender.send(preparator);
//	}

	public void sendEmail(String from, String to, String subject, String url, String template)
			throws MessagingException, IOException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		Context context = new Context();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", url);
		context.setVariables(model);

		final String templateFileName = template;
		String output = this.templateEngine.process(templateFileName, new Context(Locale.getDefault(), model));

		helper.setTo(to);
		helper.setText(output, true);
		helper.setSubject(subject);
		helper.setFrom(from);
		mailSender.send(message);
	}

	public void sendEmailTxt(String from, String to, String subject, String body)
			throws MessagingException, IOException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		helper.setTo(to);
		helper.setText(body, false);
		helper.setSubject(subject);
		helper.setFrom(from);
		mailSender.send(message);
	}
}
