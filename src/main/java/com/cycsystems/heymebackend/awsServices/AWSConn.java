package com.cycsystems.heymebackend.awsServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;

@Component
public class AWSConn {

	private Logger LOG = LogManager.getLogger(AWSConn.class);

	private static String awsUser;

	private static String awsPass;

	private static String awsRegion;

	@Value("${aws.user}")
	public void setUser(String awsUser) {
		AWSConn.awsUser = awsUser;
	}

	@Value("${aws.pass}")
	public void setPass(String awsPass) {
		AWSConn.awsPass = awsPass;
	}

	@Value("${aws.region}")
	public void setRegion(String awsRegion) {
		AWSConn.awsRegion = awsRegion;
	}

	public AmazonIdentityManagement connAws() {

		LOG.info("Start Conn AWS Service");

		BasicAWSCredentials creds = new BasicAWSCredentials(awsUser, awsPass);

		AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.standard().withRegion(awsRegion)
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();

		return iam;

	}

	public AWSConn() {
		super();
	}

}
