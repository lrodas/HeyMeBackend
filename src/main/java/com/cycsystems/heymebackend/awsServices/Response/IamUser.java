package com.cycsystems.heymebackend.awsServices.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IamUser {

	private AccessKey accesskey;
	private String UserId;
	private String arn;
	private String status;
	private String lastUsed;

}
