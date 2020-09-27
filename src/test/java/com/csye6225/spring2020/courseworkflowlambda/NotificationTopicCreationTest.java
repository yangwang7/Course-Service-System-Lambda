package com.csye6225.spring2020.courseworkflowlambda;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.amazonaws.services.lambda.runtime.Context;
import com.csye6225.spring2020.courseworkflowlambda.NotificationTopicCreation;


public class NotificationTopicCreationTest {
	private static Map<String, Object> input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
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
    public void testCreateNotificationTopic() {
    	NotificationTopicCreation handler = new NotificationTopicCreation();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        System.out.println("output: "+output);

        // TODO: validate output here if needed.
        //Assert.assertEquals("Hello from Lambda!", output);
    }
}
