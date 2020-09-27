package com.csye6225.spring2020.courseworkflowlambda;


import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.amazonaws.services.lambda.runtime.Context;
import com.csye6225.spring2020.courseworkflowlambda.SendMessage;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class SendMessageTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = "Cloud Computing";
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
    public void testsendDealMessage() {
    	SendMessage handler = new SendMessage();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
