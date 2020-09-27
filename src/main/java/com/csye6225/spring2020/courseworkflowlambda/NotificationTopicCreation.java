package com.csye6225.spring2020.courseworkflowlambda;

import java.util.Map;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class NotificationTopicCreation implements RequestHandler<Map<String, Object>, String>{

	public String handleRequest(Map<String, Object> input, Context context) {
//		ProfileCredentialsProvider creds = new ProfileCredentialsProvider();
//		creds.getCredentials();
//		ClientConfiguration cfg = new ClientConfiguration();
		AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		
		String courseId = (String) input.get("courseId");
		final CreateTopicResult createTopicResult = snsClient.createTopic(new CreateTopicRequest(courseId));
		
		return createTopicResult.getTopicArn();
	}
}
