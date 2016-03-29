package com.example.countdowntimerproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.countdowntimerproject.view.MainDownTimerView;
import com.example.countdowntimerproject.view.SecondDownTimerView;
import com.example.countdowntimerproject.view.base.OnCountDownTimerListener;

public class MainActivity extends Activity {

	private MainDownTimerView mMainDownTimerView;

	private SecondDownTimerView mSecondDownTimerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initEvent();
		startDownTimer();
	}

	private void initViews() {
		mMainDownTimerView = (MainDownTimerView) findViewById(R.id.count_down_timer_view);
		mSecondDownTimerView = (SecondDownTimerView) findViewById(R.id.second_view);
		mMainDownTimerView.setDownTime(16000000);
		mSecondDownTimerView.setDownTime(50000000);
	}

	private void initEvent() {
		mMainDownTimerView.setDownTimerListener(new OnCountDownTimerListener() {

			@Override
			public void onFinish() {
				Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT)
						.show();
			}
		});
		mSecondDownTimerView.setDownTimerListener(new OnCountDownTimerListener() {

			@Override
			public void onFinish() {
				Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	private void startDownTimer() {
		mMainDownTimerView.startDownTimer();
		mSecondDownTimerView.startDownTimer();
	}
}
