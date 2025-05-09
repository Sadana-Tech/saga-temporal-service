package com.temporal.activity;

import com.temporal.config.WorkflowStep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenericActivityImpl implements GenericActivity {
	 @Autowired
	private  RestTemplate restTemplate;

	  private static final Logger logger = LoggerFactory.getLogger(GenericActivityImpl.class);
   
    @Override
    public void invoke(WorkflowStep step)  {
        try {
            // Log the step execution for debugging purposes
            logger.info("Executing step: " + step.getActivity() + " with endpoint: " + step.getEndpoint());
            
            // Simulate an HTTP call or actual implementation
            callEndpoint(step);

            // Log successful completion
            logger.info("Step " + step.getActivity() + " completed successfully.");
        } catch (Exception e) {
            // Log any error that occurs during activity execution
        	logger.error("Step " + step.getActivity() + " failed with error: " + e.getMessage());
            throw e;
        }
    }

    private void callEndpoint(WorkflowStep step) {
    	
    	    HttpHeaders headers = new HttpHeaders();
    	    headers.setContentType(MediaType.APPLICATION_JSON);

    	    HttpEntity<String> requestEntity = new HttpEntity<>(step.getPayLoad(), headers);

    	    ResponseEntity<String> response;

    	    switch (step.getHttpMethod()) {
    	        case "GET" -> {
    	            response = restTemplate.exchange(step.getEndpoint(), HttpMethod.GET, null, String.class);
    	        }
    	        case "POST" -> {
    	            response = restTemplate.exchange(step.getEndpoint(), HttpMethod.POST, requestEntity, String.class);
    	        }
    	        case "PUT" -> {
    	            response = restTemplate.exchange(step.getEndpoint(), HttpMethod.PUT, requestEntity, String.class);
    	        }
    	        case "DELETE" -> {
    	            response = restTemplate.exchange(step.getEndpoint(), HttpMethod.DELETE, null, String.class);
    	        }
    	        default -> throw new IllegalArgumentException("Unsupported HTTP method: " + step.getHttpMethod());
    	    }

        // Simulate HTTP call or invoke the actual logic
        // For now, we simulate a success by just printing the request
    	    logger.info("Calling endpoint: " + step.getEndpoint() + " for step: " + step.getActivity());
    }
}
