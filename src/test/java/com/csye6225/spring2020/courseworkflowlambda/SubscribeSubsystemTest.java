package com.csye6225.spring2020.courseworkflowlambda;

import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.csye6225.spring2020.courseworkflowlambda.SubscribeSystem;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class SubscribeSubsystemTest {

	private static Object input;

	@BeforeClass
	public static void createInput() throws IOException {
		// TODO: set up your sample input object here.
		input = "1";
	}

	private Context createContext() {
		TestContext ctx = new TestContext();
		String topicArn = "arn:aws:sns:us-east-2:052368011189:CourseRegistrar";
		// TODO: customize your context here if needed.
		ctx.setFunctionName("subscribeSubsystem");
		ctx.setInvokedFunctionArn(topicArn);
		
		return ctx;
	}

	@Test
	public void testsubscribeWebsiteDealsSubsystem() {
		SubscribeSystem handler = new SubscribeSystem();
		Context ctx = createContext();

		String output = handler.handleRequest(input, ctx);

		// TODO: validate output here if needed.
		Assert.assertEquals("Subscribe notification sended!", output);
	}
}
