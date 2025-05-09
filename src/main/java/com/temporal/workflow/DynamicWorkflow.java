package com.temporal.workflow;

import com.temporal.config.WorkflowDefinition;
import com.temporal.model.WorkFlowRequest;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface DynamicWorkflow {

    @WorkflowMethod
    void runWorkflow(WorkFlowRequest workFlowRequest);
}
