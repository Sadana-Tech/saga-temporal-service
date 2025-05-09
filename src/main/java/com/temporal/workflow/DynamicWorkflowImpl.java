package com.temporal.workflow;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.temporal.activity.GenericActivity;
import com.temporal.config.WorkflowStep;
import com.temporal.model.WorkFlowRequest;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;


public class DynamicWorkflowImpl implements DynamicWorkflow {

    @Override
    public void runWorkflow(WorkFlowRequest workFlowRequest) {
        // Loop through each step and call the corresponding activity
    	String workflowName = workFlowRequest.getWorkFlow();
    	 Map<String, Object> searchAttributes = new HashMap<>();
         searchAttributes.put("CustomKeywordField", workflowName);
         
         Workflow.upsertSearchAttributes(searchAttributes);
         Saga saga = new Saga(new Saga.Options.Builder()
                 .setParallelCompensation(false)
                 .build());
			/*
			 * List<WorkflowStep> steps = workFlowRequest.getSteps();
			 * steps.sort(Comparator.comparingInt(s -> Integer.parseInt(s.getOrder())));
			 */
        for (WorkflowStep step : workFlowRequest.getSteps()) {
            try {

            invokeActivity(step, saga);
            
            } catch (Exception e) {
            	  // Only register compensation after successful invoke
                if (step.getCompensationStep() != null) {
                	WorkflowStep compStep = step.getCompensationStep();
                	invokeActivity(compStep, saga);
                }
                throw Workflow.wrap(e);
            }
        }
    }
    private void invokeActivity(WorkflowStep step, Saga saga) {
        // Create activity options
        ActivityOptions options = ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofMinutes(step.getStartToCloseTimeout()))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setInitialInterval(Duration.ofSeconds(step.getRetryInitialInterval()))
                        .setMaximumInterval(Duration.ofSeconds(step.getRetryMaxInterval()))
                        .setBackoffCoefficient(step.getRetryPolicyBackOff())
                        .setMaximumAttempts(step.getRetryCount())
                        
                        .build())
                .build();

        GenericActivity activity = Workflow.newActivityStub(GenericActivity.class, options);

        // Log and invoke main activity
        Workflow.getLogger(getClass()).info("Invoking main activity: " + step.getActivity());
        activity.invoke(step); // if this fails, we never reach next lines
        Workflow.getLogger(getClass()).info("Main activity succeeded: " + step.getActivity());

      
    }
	/*
	 * private void invokeActivity(WorkflowStep step,Saga saga) { // Create activity
	 * options ActivityOptions options = ActivityOptions.newBuilder()
	 * .setStartToCloseTimeout(Duration.ofMinutes(1))
	 * .setRetryOptions(RetryOptions.newBuilder()
	 * .setInitialInterval(Duration.ofSeconds(1))
	 * .setMaximumInterval(Duration.ofSeconds(10)) .setBackoffCoefficient(2.0)
	 * .setMaximumAttempts(step.getRetryCount()) .build()) .build();
	 * 
	 * // Use Workflow.newActivityStub to call activities GenericActivity activity =
	 * Workflow.newActivityStub(GenericActivity.class, options);
	 * 
	 * // Invoke the activity
	 * Workflow.getLogger(getClass()).info("Invoking main activity: " +
	 * step.getActivity()); activity.invoke(step);
	 * Workflow.getLogger(getClass()).info("Main activity succeeded: " +
	 * step.getActivity());
	 * 
	 * ActivityOptions compensationOptions = ActivityOptions.newBuilder()
	 * .setStartToCloseTimeout(Duration.ofMinutes(1)) .setRetryOptions(
	 * RetryOptions.newBuilder()
	 * .setMaximumAttempts(step.getCompensationStep().getRetryCount()) .build())
	 * .build();
	 * 
	 * 
	 * 
	 * if (step.getCompensationStep() != null) { WorkflowStep compensationStep =
	 * step.getCompensationStep(); saga.addCompensation(() -> { try {
	 * Workflow.getLogger(getClass()).info("Executing compensation: " +
	 * compensationStep.getActivity()); activity.invoke(compensationStep); } catch
	 * (Exception ce) { Workflow.getLogger(getClass()).error("Compensation failed: "
	 * + compensationStep.getActivity(), ce); throw ce; } }); }
	 * 
	 * }
	 */
}
