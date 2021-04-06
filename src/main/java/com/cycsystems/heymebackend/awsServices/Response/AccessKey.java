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
public class AccessKey {

	private String accessKeyId;
	private String status;
	private String secretAccessKey;

}
