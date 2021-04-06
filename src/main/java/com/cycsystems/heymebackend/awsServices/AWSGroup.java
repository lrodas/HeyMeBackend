package com.cycsystems.heymebackend.awsServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.identitymanagement.model.AddUserToGroupRequest;
import com.amazonaws.services.identitymanagement.model.AddUserToGroupResult;
import com.amazonaws.services.identitymanagement.model.RemoveUserFromGroupRequest;

@Component
public class AWSGroup {

	private static Logger LOG = LogManager.getLogger(AWSGroup.class);

	private static String awsGroupSendEmailOnly;

	@Value("${aws.name.group}")
	public void setGroup(String awsGroupSendEmailOnly) {
		AWSGroup.awsGroupSendEmailOnly = awsGroupSendEmailOnly;
	}

	private AWSConn conn = new AWSConn();

	public String addUsrToGroup(String user) {

		LOG.info("METHOD(): addUsrToGroup");

		String response = "";

		try {
			AddUserToGroupRequest requestGroup = new AddUserToGroupRequest().withGroupName(awsGroupSendEmailOnly)
					.withUserName(user);
			AddUserToGroupResult responseGroup = conn.connAws().addUserToGroup(requestGroup);

			if (responseGroup.getSdkHttpMetadata().getHttpStatusCode() == 200) {

				response = String.valueOf(responseGroup.getSdkHttpMetadata().getHttpStatusCode());
			} else {
				response = "01";
			}
		} catch (Exception e) {
			response = e.getMessage();
		}

		return response;
	}

	public Object deleteUsrToGroup(String user) {

		LOG.info("METHOD(): deleteUsrToGroup");

		Object response = null;

		RemoveUserFromGroupRequest requestGroup = new RemoveUserFromGroupRequest().withGroupName(awsGroupSendEmailOnly)
				.withUserName(user);
		try {
			response = conn.connAws().removeUserFromGroup(requestGroup);
		} catch (Exception e) {
			response = "Error to Delete Group";
		}

		return response;
	}

}
