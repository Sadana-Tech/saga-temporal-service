package com.temporal.config;

import java.util.List;

import lombok.Data;

@Data
public class WorkflowDefinition {
    private String workFlow;
    private List<WorkflowStep> steps;

  }
