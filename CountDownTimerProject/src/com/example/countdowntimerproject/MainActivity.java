package com.example.countdowntimerproject;

import android.app.Activity;
import android.os.Bundle;

import com.example.countdowntimerproject.view.MainDownTimerView;
import com.example.countdowntimerproject.view.SecondDownTimerView;

public class MainActivity extends Activity {

	private MainDownTimerView mMainDownTimerView;

	private SecondDownTimerView mSecondDownTimerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		startDownTimer();
	}

	private void initViews() {
		mMainDownTimerView = (MainDownTimerView) findViewById(R.id.count_down_timer_view);
		mSecondDownTimerView = (SecondDownTimerView) findViewById(R.id.second_view);
		mMainDownTimerView.setDownTime(16000000);
		mSecondDownTimerView.setDownTime(50000000);
	}

	private void startDownTimer() {
		mMainDownTimerView.startDownTimer();
		mSecondDownTimerView.startDownTimer();
	}
	
	
	
}
