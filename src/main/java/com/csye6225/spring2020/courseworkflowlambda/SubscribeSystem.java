package com.csye6225.spring2020.courseworkflowlambda;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SubscribeSystem implements RequestHandler<Map<String, Object>, String> {
	
	public String subscribe(String input, String arn, Context context) {

		context.getLogger().log("Success: into subscribe() - input: "+input);
		// Subscribe an email endpoint to an Amazon SNS topic.
    	final SubscribeRequest subscribeRequest = new SubscribeRequest(arn, "email", input);
//    	ProfileCredentialsProvider creds = new ProfileCredentialsProvider();
//		creds.getCredentials();
//    	ClientConfiguration cfg = new ClientConfiguration();
		AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		snsClient.subscribe(subscribeRequest);

    	// Print the request ID for the SubscribeRequest action.
    	System.out.println("SubscribeRequest: " + snsClient.getCachedResponseMetadata(subscribeRequest));
    	System.out.println("To confirm the subscription, check your email.");

        // TODO: implement your handler
        return "Hello from Lambda!";
	}

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
		String profId = (String) input.get("professorId");

		context.getLogger().log("profId: "+ profId);
		Professor prof = getProfessor(profId);
		context.getLogger().log("Success: get professor");
		subscribe(prof.getEmail(), (String) input.get("notificationTopic"), context);
		
		return "Subscribe notification sended!";
    }
    
    private Professor getProfessor(String profId) {
		final Client client = ClientBuilder.newClient();
		final WebTarget webTarget = client
				.target("http://assignment3-env.eba-3ugzacjq.us-east-2.elasticbeanstalk.com/webapi")
				.path("professors/" + profId);
		final Professor response = webTarget.request().accept(MediaType.APPLICATION_JSON).get(Professor.class);

		return response;
	}

}

class Professor {
	private String professorId;
	private String firstName;
	private String lastName;
	private String department;
	private String joiningDate;
	private String email;
	
	public Professor() {
	}
	
	public Professor(String professorId, String firstName, String lastName, String department, String joiningDate, String email) {
		this.professorId = professorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.joiningDate = joiningDate;
		this.email = email;
	}

	public String getProfessorId() {
		return professorId;
	}
	public void setProfessorId(String professorId) {
		this.professorId = professorId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() { 
		return "ProfId=" + getProfessorId() + ", firstName=" + getFirstName() + ", lastName="+ getLastName()
				+ ", department=" + getDepartment() + ", joiningDate=" + getJoiningDate() + ", email = " + email;
	}
}