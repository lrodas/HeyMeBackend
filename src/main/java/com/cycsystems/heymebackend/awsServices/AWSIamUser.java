package com.cycsystems.heymebackend.awsServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.DeleteUserRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.amazonaws.services.identitymanagement.model.User;
import com.cycsystems.heymebackend.awsServices.Response.AccessKey;
import com.cycsystems.heymebackend.awsServices.Response.IamUser;

@Component
public class AWSIamUser {

	private Logger LOG = LogManager.getLogger(AWSIamUser.class);

	private AWSConn conn = new AWSConn();
	private final AWSGroup group = new AWSGroup();
	private final AWSAccessKey accessKey = new AWSAccessKey();

	public IamUser addIAMUser(String user) {

		LOG.info("METHOD(): addIAMUser");

		AccessKey responseKey;
		IamUser iamUser = new IamUser();
		CreateUserResult responseUser = new CreateUserResult();

		try {
			CreateUserRequest requestUser = new CreateUserRequest().withUserName(user);

			responseUser = conn.connAws().createUser(requestUser);

			iamUser.setUserId(responseUser.getUser().getUserId());
			iamUser.setArn(responseUser.getUser().getArn());

			if (responseUser.getUser().getPasswordLastUsed() != null) {
				iamUser.setLastUsed(responseUser.getUser().getPasswordLastUsed().toString());
			}

			if (responseUser.getSdkHttpMetadata().getHttpStatusCode() == 200) {

				String responseGroup = group.addUsrToGroup(user);

				if (responseGroup.equals("200")) {

					responseKey = accessKey.addAccessKeyToUser(user);
					iamUser.setAccesskey(responseKey);

					if (responseKey.getStatus().equals("Active")) {
						iamUser.setStatus("200");
					} else {
						group.deleteUsrToGroup(user);
						deleteIAMUser(user);
					}

				} else {
					deleteIAMUser(user);
				}

			} else {
				iamUser.setStatus("01");
				deleteIAMUser(user);
			}
		} catch (Exception e) {

			iamUser.setStatus(e.getMessage());

		}

		return iamUser;
	}

	public IamUser obtainIAMUser(String user) {
		IamUser iamUser = new IamUser();

		boolean done = false;

		ListUsersRequest request = new ListUsersRequest();
		ListUsersResult response = null;

		while (!done) {
			response = conn.connAws().listUsers(request);

			for (User userObtain : response.getUsers()) {
				System.out.println(userObtain);
				iamUser.setArn(userObtain.getArn());
				iamUser.setUserId(userObtain.getUserId());
				iamUser.setStatus("200");
				if (userObtain.getPasswordLastUsed() != null) {
					iamUser.setLastUsed(userObtain.getPasswordLastUsed().toString());
				}

			}

			request.setMarker(response.getMarker());

			if (!response.getIsTruncated()) {
				done = true;
			}
		}

		return iamUser;
	}

	public Object deleteIAMUser(String user) {

		LOG.info("METHOD(): deleteIAMUser");

		Object response = null;

		DeleteUserRequest requestUser = new DeleteUserRequest().withUserName(user);

		try {
			response = conn.connAws().deleteUser(requestUser);
		} catch (Exception e) {
			response = "Error to Delete IAM User";
		}

		return response;
	}

	public Object deleteAllIAMUser(String idKey, String user) {

		LOG.info("METHOD(): deleteAllIAMUser");

		Object response = null;

		Object delGroup = group.deleteUsrToGroup(user);

		Object delKey = accessKey.deleteAccessKeyToUser(idKey, user);

		DeleteUserRequest requestUser = new DeleteUserRequest().withUserName(user);

		try {
			response = conn.connAws().deleteUser(requestUser);
		} catch (Exception e) {
			response = "Error to Delete IAM user";
		}

		return response;
	}

}
