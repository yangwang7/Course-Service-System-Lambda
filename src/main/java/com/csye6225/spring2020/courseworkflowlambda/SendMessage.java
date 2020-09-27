package com.csye6225.spring2020.courseworkflowlambda;


import java.util.Map;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SendMessage implements RequestHandler<Map<String, Object>, String> {

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
//		ProfileCredentialsProvider creds = new ProfileCredentialsProvider();
//		creds.getCredentials();
//    	ClientConfiguration cfg = new ClientConfiguration();
		AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
    	
    	// Publish a message to an Amazon SNS topic.
    	final String msg = "New Notification: Course " + input.toString() + " is registered.";
		final PublishRequest publishRequest = new PublishRequest((String) input.get("notificationTopic"), msg);
    	final PublishResult publishResponse = snsClient.publish(publishRequest);

    	// Print the MessageId of the message.
    	System.out.println("MessageId: " + publishResponse.getMessageId());
    	
        return "Hello from Lambda!";
    }

}
