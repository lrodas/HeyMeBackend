//package com.cycsystems.heymebackend.util;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.auth.BasicSessionCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
//import com.amazonaws.services.securitytoken.model.Credentials;
//import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
//import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
//import com.amazonaws.services.simpleemail.model.GetIdentityMailFromDomainAttributesRequest;
//import com.amazonaws.services.simpleemail.model.GetIdentityMailFromDomainAttributesResult;
//import com.amazonaws.services.simpleemail.model.GetIdentityNotificationAttributesRequest;
//import com.amazonaws.services.simpleemail.model.GetIdentityNotificationAttributesResult;
//import com.amazonaws.services.simpleemail.model.GetSendQuotaRequest;
//import com.amazonaws.services.simpleemail.model.GetSendQuotaResult;
//import com.amazonaws.services.simpleemail.model.GetSendStatisticsRequest;
//import com.amazonaws.services.simpleemail.model.GetSendStatisticsResult;
//import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesRequest;
//import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesResult;
//import com.amazonaws.services.simpleemail.model.SetIdentityFeedbackForwardingEnabledRequest;
//import com.amazonaws.services.simpleemail.model.SetIdentityFeedbackForwardingEnabledResult;
//import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
//import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
//import com.amazonaws.services.identitymanagement.model.ListUsersRequest;
//import com.amazonaws.services.identitymanagement.model.ListUsersResult;
//import com.amazonaws.services.identitymanagement.model.User;
//
//public class Test {
//
//	public static void main(String[] args) {
//
////		BasicAWSCredentials creds = new BasicAWSCredentials("AKIAVEOQYAC4JOBOQM5F",
////				"pg1JdIyKumm+XO0ZnmYlfs0zkURNWVltWA+kJSzW");
////
////		AmazonIdentityManagement ima = AmazonIdentityManagementClientBuilder.standard().withRegion("us-east-2")
////				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
////
////		boolean done = false;
////
////		ListUsersRequest request = new ListUsersRequest();
////		ListUsersResult response = null;
////
////		while (!done) {
////			response = ima.listUsers(request);
////
////			for (User user : response.getUsers()) {
////				System.out.format("Usuarios obtenidos: " + user.getUserName() + " ////// " + user.getUserId()
////						+ " ////// " + user.getArn() + " ////// " + user.getCreateDate());
////			}
////
////			request.setMarker(response.getMarker());
////
////			if (!response.getIsTruncated()) {
////				done = true;
////			}
////		}
//
//		users();
//	}
//
//	private static void users() {
//
//		BasicAWSCredentials creds = new BasicAWSCredentials("AKIAVEOQYAC4DJDYMM5C",
//				"Q/taXHbuk7rEv/l2sZd/jk1rrKooueQuhe47aobV");
//		
////		BasicAWSCredentials creds = new BasicAWSCredentials("AKIAVEOQYAC4JOBOQM5F",
////				"pg1JdIyKumm+XO0ZnmYlfs0zkURNWVltWA+kJSzW");
//
//		System.out.println("******************************** Credenciales: ");
//
//		AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_2)
//				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
//
//		System.out.println("******************************** conexion: ");
//
//		GetSendStatisticsRequest request = new GetSendStatisticsRequest();
//
//		System.out.println("******************************** request: " + request);
//
//		GetSendStatisticsResult response = client.getSendStatistics();
//
//		System.out.println("Respuesta: " + response.getSdkHttpMetadata());
//		System.out.println("Respuesta: " + response.getSdkResponseMetadata());
//		System.out.println("Respuesta: " + response.getSendDataPoints());
//		System.out.println("Respuesta: " + response.getSdkResponseMetadata().getRequestId());
//		System.out.println("Respuesta: " + response);
//
//		ListVerifiedEmailAddressesRequest request1 = new ListVerifiedEmailAddressesRequest();
//		ListVerifiedEmailAddressesResult response1 = client.listVerifiedEmailAddresses(request1);
//
//		System.out.println("Respuesta: " + response1.getSdkHttpMetadata());
//		System.out.println("Respuesta: " + response1.getSdkResponseMetadata());
//		System.out.println("Respuesta: " + response1.getSdkResponseMetadata().getRequestId());
//		System.out.println("Respuesta: " + response1);
//
////		SetIdentityFeedbackForwardingEnabledRequest request2 = new SetIdentityFeedbackForwardingEnabledRequest()
////				.withIdentity("joselito9129@gmail.com").withForwardingEnabled(true);
////		SetIdentityFeedbackForwardingEnabledResult response2 = client.setIdentityFeedbackForwardingEnabled(request2);
////
////		System.out.println("Respuesta: " + response2.getSdkHttpMetadata());
////		System.out.println("Respuesta: " + response2.getSdkResponseMetadata());
////		System.out.println("Respuesta: " + response2.getSdkResponseMetadata().getRequestId());
////		System.out.println("Respuesta: " + response2);
//
//		GetIdentityMailFromDomainAttributesRequest request3 = new GetIdentityMailFromDomainAttributesRequest()
//				.withIdentities("heyme.com.gt");
//		GetIdentityMailFromDomainAttributesResult response3 = client.getIdentityMailFromDomainAttributes(request3);
//
//		System.out.println("Respuesta: " + response3.getSdkHttpMetadata());
//		System.out.println("Respuesta: " + response3.getSdkResponseMetadata());
//		System.out.println("Respuesta: " + response3.getSdkResponseMetadata().getRequestId());
//		System.out.println("Respuesta: " + response3);
//		
//		
//		
//		GetIdentityNotificationAttributesRequest request4 = new GetIdentityNotificationAttributesRequest().withIdentities("heyme.com.gt");
//		GetIdentityNotificationAttributesResult response4 = client.getIdentityNotificationAttributes(request4);
//		
//		System.out.println("Respuesta: " + response4.getSdkHttpMetadata());
//		System.out.println("Respuesta: " + response4.getSdkResponseMetadata());
//		System.out.println("Respuesta: " + response4.getSdkResponseMetadata().getRequestId());
//		System.out.println("Respuesta: " + response4);
//		
//		
//		
//		GetSendQuotaRequest request5 = new GetSendQuotaRequest();
//		GetSendQuotaResult response5 = client.getSendQuota(request5);
//		System.out.println("Respuesta: " + response5);
//		
//		
//		
//		GetSendStatisticsRequest request6 = new GetSendStatisticsRequest();
//		GetSendStatisticsResult response6 = client.getSendStatistics(request6);
//		System.out.println("Respuesta: " + response);
//		System.out.println("Respuesta: " + response1);
//		System.out.println("Respuesta: " + response3);
//		System.out.println("Respuesta: " + response4);
//		System.out.println("Respuesta: " + response5);
//		System.out.println("Respuesta: " + response6);
//	}
//
//}
