package com.temporal.model;

import lombok.Data;

@Data
public class PaymentDetails {

	private double amount;
	private String pmtMethod;
	private String name;
	private String mobileNo;
	private String trnId;
	private boolean errorFlag; 

}
