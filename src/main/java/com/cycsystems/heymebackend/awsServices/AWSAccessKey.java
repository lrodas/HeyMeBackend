package com.cycsystems.heymebackend.awsServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.identitymanagement.model.CreateAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyResult;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyRequest;
import com.cycsystems.heymebackend.awsServices.Response.AccessKey;

public class AWSAccessKey {

	private Logger LOG = LogManager.getLogger(AWSAccessKey.class);

	private AWSConn conn = new AWSConn();

	public AccessKey addAccessKeyToUser(String user) {

		LOG.info("METHOD(): addAccessKeyToUser");

		AccessKey response = new AccessKey();

		try {
			CreateAccessKeyRequest requestKey = new CreateAccessKeyRequest().withUserName(user);
			CreateAccessKeyResult responseKey = conn.connAws().createAccessKey(requestKey);

			if (responseKey.getSdkHttpMetadata().getHttpStatusCode() == 200) {

				response.setAccessKeyId(responseKey.getAccessKey().getAccessKeyId());
				response.setSecretAccessKey(responseKey.getAccessKey().getSecretAccessKey());
				response.setStatus(responseKey.getAccessKey().getStatus());

			} else {
				response.setStatus("01");
			}
		} catch (Exception e) {
			response.setStatus(e.getMessage());
		}

		return response;
	}

	public Object deleteAccessKeyToUser(String keyId, String user) {

		LOG.info("METHOD(): deleteAccessKeyToUser");

		Object response = null;

		DeleteAccessKeyRequest requestKey = new DeleteAccessKeyRequest().withAccessKeyId(keyId).withUserName(user);
		try {
			response = conn.connAws().deleteAccessKey(requestKey);
		} catch (Exception e) {
			response = "Error to Delete Access Key";
		}

		return response;
	}

}
