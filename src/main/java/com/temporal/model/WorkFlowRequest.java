package com.temporal.model;

import java.util.ArrayList;
import java.util.List;

import com.temporal.config.WorkflowStep;

import lombok.Data;

@Data
public class WorkFlowRequest {

	 private String workFlow;
	    private List<WorkflowStep> steps;

	

}
