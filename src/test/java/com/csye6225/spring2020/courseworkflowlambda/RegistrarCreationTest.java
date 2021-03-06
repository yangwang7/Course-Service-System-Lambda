package com.csye6225.spring2020.courseworkflowlambda;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.csye6225.spring2020.courseworkflowlambda.RegistrarCreation;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RegistrarCreationTest {

    private static Map<String, String> input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testRegistrarCreation() {
        RegistrarCreation handler = new RegistrarCreation();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}