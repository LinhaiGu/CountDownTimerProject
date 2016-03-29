package com.example.countdowntimerproject.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.countdowntimerproject.view.base.BaseCountDownTimerView;

public class SecondDownTimerView extends BaseCountDownTimerView{

	public SecondDownTimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SecondDownTimerView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SecondDownTimerView(Context context) {
		this(context,null);
	}

	@Override
	protected String getStrokeColor() {
		return "CC9999";
	}

	@Override
	protected String getTextColor() {
		return "6666CC";
	}

	@Override
	protected int getCornerRadius() {
		return 2;
	}

	@Override
	protected int getTextSize() {
		return 18;
	}

	@Override
	protected String getBackgroundColor() {
		return "FF9966";
	}
	
}
