package com.csye6225.spring2020.courseworkflowlambda;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CourseCreation implements RequestHandler<DynamodbEvent, Integer> {
	@Override
	public Integer handleRequest(DynamodbEvent event, Context context) {
		if(event == null) {
			context.getLogger().log("Event is NULL! ");
			return -1;
		}
		
		context.getLogger().log("Received event: " + event.toString());

		final AWSStepFunctions stepFunctionsClient = getStepFunctionsClient();
		final StartExecutionRequest request = new StartExecutionRequest();
		request.setStateMachineArn("arn:aws:states:us-east-2:052368011189:stateMachine:CourseWorkflow");
		final ObjectMapper jsonMapper = new ObjectMapper();
		
		
		if(event.getRecords() == null) {
			context.getLogger().log("Have no records! ");
			return -1;
		}
		
		context.getLogger().log("Records: " + event.getRecords());

		for (final DynamodbStreamRecord record : event.getRecords()) {
			context.getLogger().log("record = "+record);
			if(record == null) break;
			
			context.getLogger().log(record.getEventID());
			context.getLogger().log(record.getEventName());
			context.getLogger().log(record.getDynamodb().toString());
			
			if (!record.getEventName().equals("INSERT")) {
				continue;
			}

			context.getLogger().log("Success: get event name");
			
			final Map<String, AttributeValue> item = record.getDynamodb().getNewImage();
			
			context.getLogger().log("Success: item = "+ item);
			context.getLogger().log("Success: courseId = "+ item.get("courseId"));
			
			if (item == null || item.get("courseId") == null) {
				continue;
			}

			context.getLogger().log("Success: get courseId");
			
			try {
				context.getLogger().log("Success: into TRY");
				
				request.setInput(jsonMapper.writeValueAsString(generateInput(item, context)));
				context.getLogger().log("Success: 111111");
				final StartExecutionResult result = stepFunctionsClient.startExecution(request);

				context.getLogger().log("Success: 222222");
				context.getLogger().log("Event: " + record.getEventID() + ", Result: " + result.toString());
			} catch (JsonProcessingException e) {
				context.getLogger().log("Error in generateInput, Event: " + record.getEventID());
			}
		}

		context.getLogger().log("I get out of for loop!!!");
		return event.getRecords().size();
	}

	private Map<String, Object> generateInput(final Map<String, AttributeValue> item, Context context) {
		final Map<String, Object> input = new HashMap<>();
		
		context.getLogger().log("Input = "+input);
		context.getLogger().log("courseName = "+item.get("courseName"));
		
		input.put("courseId", item.get("courseId") != null ? item.get("courseId").getS() : "");
		input.put("department", item.get("department") != null ? item.get("department").getS() : "");
		input.put("boardId", item.get("boardId") != null ? item.get("boardId").getS() : "");
		input.put("roster", item.get("roster") != null ? item.get("roster").getSS() : "");
		input.put("notificationTopic", item.get("notificationTopic") != null ? item.get("notificationTopic").getS() : "");
		input.put("professorId", item.get("professorId") != null ? item.get("professorId").getS() : "");
		input.put("lectures", item.get("lectures") != null ? item.get("lectures").getSS() : "");
		input.put("taId", item.get("taId") != null ? item.get("taId").getS() : "");
		input.put("courseName", item.get("courseName") != null ? item.get("courseName").getS() : "");
		
		context.getLogger().log("Input2 = "+input);

		return input;
	}

	private AWSStepFunctions getStepFunctionsClient() {
		return AWSStepFunctionsClientBuilder.standard().withRegion("us-east-2").build();
	}
}