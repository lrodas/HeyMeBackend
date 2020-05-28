package com.cycsystems.heymebackend.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailServiceImpl {

	@Autowired
	private JavaMailSender sender;
	
	public void sendMail(String from, String to, String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(to);
                message.setSubject(subject);
                message.setSentDate(new Date());
                message.setFrom(from);
                message.setText(body, true);
            }
        };
        this.sender.send(preparator);
    }
}
