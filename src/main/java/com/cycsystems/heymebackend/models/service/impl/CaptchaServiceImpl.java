package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.common.RecaptchaResponse;
import com.cycsystems.heymebackend.models.service.ICaptchaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaServiceImpl implements ICaptchaService {

	@Autowired
	private RestTemplate restTemplate;

	private Logger LOG = LogManager.getLogger(CaptchaServiceImpl.class);

	@Value("${google.recaptcha.secret.key}")
	public String recaptchaSecret;
	@Value("${google.recaptcha.verify.url}")
	public String recaptchaVerifyUrl;

	public boolean verify(String response) {
		LOG.info("METHOD: verify() --PARAMS: " + response);

		MultiValueMap<Object, Object> param = new LinkedMultiValueMap<>();
		param.add("secret", recaptchaSecret);
		param.add("response", response);

		RecaptchaResponse recaptchaResponse = null;
		try {
			recaptchaResponse = this.restTemplate.postForObject(recaptchaVerifyUrl, param, RecaptchaResponse.class);
		} catch (RestClientException e) {
			System.out.print(e.getMessage());
		}
		LOG.info("recaptchaResponse: " + recaptchaResponse);
		if (recaptchaResponse.isSuccess()) {
			return true;
		} else {
			return false;
		}
	}
}
