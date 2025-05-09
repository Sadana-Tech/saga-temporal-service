package com.temporal.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.temporal.model.WorkFlowRequest;
import com.temporal.workflow.DynamicWorkflow;


import io.temporal.client.WorkflowClient;

@RestController
public class WorkFlowController {
	@Autowired
	private  WorkflowClient workflowClient;
	public static final String TASK_QUEUE = "DSL_TASK_QUEUE";

	
	

	@PostMapping("/workflow")
	public void workflow(@RequestBody WorkFlowRequest workFlowRequest) {

		DynamicWorkflow dynamicWorkflow = workflowClient.newWorkflowStub(DynamicWorkflow.class,
				io.temporal.client.WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).setWorkflowId(workFlowRequest.getWorkFlow()+"-"+UUID.randomUUID().toString()).
				build());
		   dynamicWorkflow.runWorkflow(workFlowRequest);
		

	}

}
