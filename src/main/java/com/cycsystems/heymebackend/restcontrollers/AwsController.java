package com.cycsystems.heymebackend.restcontrollers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.DeleteConflictException;
import com.amazonaws.services.identitymanagement.model.DeleteUserRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.amazonaws.services.identitymanagement.model.UpdateUserRequest;
import com.amazonaws.services.identitymanagement.model.UpdateUserResult;
import com.amazonaws.services.identitymanagement.model.User;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.cycsystems.heymebackend.util.Constants;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/IAMUser")
public class AwsController {

	@Async
	@PostMapping("/obtainIAMUser")
	public AsyncResult<ResponseEntity<Object>> activarUsuario() {

		BasicAWSCredentials creds = new BasicAWSCredentials("AKIARMWTPMHWWXAYL3V6",
				"C9OJ/PVBKOzycpAcvbEj+tFMgEfgrJJIpNlIoh+t");

//		AmazonS3 amazonS3 = AmazonS3Client.builder().withRegion("us-east-2")
//				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
//
////		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIARMWTPMHW2SUGFBPZ",
////				"BOrn1OnwcR/Fkj4G8mEthhnU+yehKJbtBan4QnB5b7Cp");
////		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
////				.build();
//
//		List<Bucket> bucketLst = amazonS3.listBuckets();
//
//		bucketLst.stream().forEach(bicket -> {
//			System.out.println("Nombre: " + bicket.getName() + " Propietario: " + bicket.getOwner()
//					+ " Fecha de creacion: " + bicket.getCreationDate());
//		});
//
//		Object result = null;

		AmazonIdentityManagement ima = AmazonIdentityManagementClientBuilder.standard().withRegion("us-east-2")
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();

		boolean done = false;

		ListUsersRequest request = new ListUsersRequest();
		ListUsersResult response = null;

		while (!done) {
			response = ima.listUsers(request);

			for (User user : response.getUsers()) {
				System.out.format("Usuarios obtenidos: " + user.getUserName() + " ////// " + user.getUserId()
						+ " ////// " + user.getArn() + " ////// " + user.getCreateDate());
			}

			request.setMarker(response.getMarker());

			if (!response.getIsTruncated()) {
				done = true;
			}
		}

		return new AsyncResult<>(ResponseEntity.ok(response.getUsers()));
	}

	@Async
	@PostMapping("/createUsrIAM")
	public AsyncResult<ResponseEntity<Object>> createUsrIAM() {

		BasicAWSCredentials creds = new BasicAWSCredentials("AKIARMWTPMHWWXAYL3V6",
				"C9OJ/PVBKOzycpAcvbEj+tFMgEfgrJJIpNlIoh+t");

		AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.standard().withRegion("us-east-2")
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();

		CreateUserRequest request = new CreateUserRequest().withUserName("UserTestJava");
		CreateUserResult response = iam.createUser(request);

		System.out.println("Request de creacion: " + response);
		System.out.println("Request de creacion: " + response.getUser());

		return new AsyncResult<>(ResponseEntity.ok(response));
	}

	@Async
	@PostMapping("updateUsrIAM")
	public AsyncResult<ResponseEntity<Object>> updateUsrIAM() {

		BasicAWSCredentials creds = new BasicAWSCredentials("AKIARMWTPMHWWXAYL3V6",
				"C9OJ/PVBKOzycpAcvbEj+tFMgEfgrJJIpNlIoh+t");

		AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.standard().withRegion("us-east-2")
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();

		UpdateUserRequest request = new UpdateUserRequest().withUserName("UserTestJava")
				.withNewUserName("UserTestEditJava");
		UpdateUserResult response = iam.updateUser(request);

		System.out.println("Request de creacion: " + response);

		return new AsyncResult<>(ResponseEntity.ok(response));
	}

	@Async
	@PostMapping("deleteUsrIAM")
	public AsyncResult<ResponseEntity<Object>> deleteUsrIAM() {

		Object response = null;

		BasicAWSCredentials creds = new BasicAWSCredentials("AKIARMWTPMHWWXAYL3V6",
				"C9OJ/PVBKOzycpAcvbEj+tFMgEfgrJJIpNlIoh+t");

		AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.standard().withRegion("us-east-2")
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();

		DeleteUserRequest request = new DeleteUserRequest().withUserName("UserTestEditJava");
		try {
			response = iam.deleteUser(request);

		} catch (DeleteConflictException e) {
			System.out.println("Unable to delete user. Verify user is not" + " associated with any resources");
			throw e;
		}

		System.out.println("Request de creacion: " + response);
		return new AsyncResult<>(ResponseEntity.ok(response));
	}
}
