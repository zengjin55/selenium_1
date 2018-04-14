package com.essa.framework;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.essa.framework.SendEmail;

public class ListenerSuite implements ISuiteListener {

	private String[] args;

	public void onFinish(ISuite arg0) {
		SendEmail.main(args);
	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub
	}

}
