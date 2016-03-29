# CountDownTimerProject
仿京东首页倒计时控件
前言

这几天翻看之前写的一些代码，感觉整体的代码设计上有点缺陷，为此买了本《设计模式之禅》，随说之前也研究过设计模式，但长时间不用忘了差不多，买本书补充下知识。 

今天给大家带来的是自定义倒计时控件，为什么要写这个，是因为今天在京东首页看到了这个全场秒杀的倒计时控件，感觉挺不错的，于是乎写给大家看看，也算是一个思路吧，当然实现这种样式的控件方法有很多，大家可以按照自己的思路来写，那我这个倒计时有什么特点呢？可定制，颜色，背景，四周角度，文字大小都可定制。

先来看看效果吧：

http://img.blog.csdn.net/20160329124233862

倒计时控件

如何使用

下载项目后将里面相关代码复制过去，创建一个继承BaseCountDownTimerView的类。

如以下：

package com.example.countdowntimerproject.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.countdowntimerproject.view.base.BaseCountDownTimerView;

public class MainDownTimerView extends BaseCountDownTimerView{

    public MainDownTimerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MainDownTimerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MainDownTimerView(Context context) {
        this(context,null);
    }

    @Override
    protected String getStrokeColor() {
        return "333333";
    }

    @Override
    protected String getTextColor() {
        return "000033";
    }

    @Override
    protected int getCornerRadius() {
        return 1;
    }

    @Override
    protected int getTextSize() {
        return 15;
    }

    @Override
    protected String getBackgroundColor() {
        return null;
    }



}

创建继承自BaseCountDownTimerView的类时会重写几个方法，这几个方法含义如下：

/**
     * 获取边框颜色
     * 
     * @return
     */
    protected abstract String getStrokeColor();

    /**
     * 设置背景色
     * 
     * @return
     */
    protected abstract String getBackgroundColor();

    /**
     * 获取文字颜色
     * 
     * @return
     */
    protected abstract String getTextColor();

    /**
     * 获取边框圆角
     * 
     * @return
     */
    protected abstract int getCornerRadius();

    /**
     * 获取标签文字大小
     * 
     * @return
     */
    protected abstract int getTextSize();

创建完毕后，创建我们的xml:

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >


    <com.example.countdowntimerproject.view.MainDownTimerView
        android:id="@+id/count_down_timer_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true" >
    </com.example.countdowntimerproject.view.MainDownTimerView>

</RelativeLayout>

接着在Activity中使用：

package com.example.countdowntimerproject;

import android.app.Activity;
import android.os.Bundle;

import com.example.countdowntimerproject.view.MainDownTimerView;

public class MainActivity extends Activity {

    private MainDownTimerView mMainDownTimerView;


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
        mMainDownTimerView.setDownTime(16000000);
    }

    private void initEvent() {
        mMainDownTimerView.setDownTimerListener(new OnCountDownTimerListener() {

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void startDownTimer() {
        mMainDownTimerView.startDownTimer();
    }

}

setDownTime方法传入的是总的毫秒数，最后通过startDownTimer方法开启倒计时，cancelDownTimer方法是取消倒计时。

自定义倒计时控件原理

整个控件的容器是LinearLayout，因此创建继承自LinearLayout的抽象类BaseCountDownTimerView，控件内的自控件是水平排列：

public abstract class BaseCountDownTimerView extends LinearLayout {

    public BaseCountDownTimerView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public BaseCountDownTimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCountDownTimerView(Context context) {
        this(context, null);
    }

    private void init() {
        this.setOrientation(HORIZONTAL);// 设置布局排列方式
        createView();// 创造三个标签
        addLabelView();// 添加标签到容器中
    }

标签的创建请查看《自定义瀑布流式的标签列表》这篇文章，创建完标签后，将标签添加到容器中去：

    /**
     * 创建时、分、秒的标签
     */
    private void createView() {
        mHourTextView = createLabel();
        mMinTextView = createLabel();
        mSecondTextView = createLabel();
    }

    /**
     * 添加标签到容器中
     */
    private void addLabelView() {
        removeAllViews();
        this.addView(mHourTextView);
        this.addView(createColon());
        this.addView(mMinTextView);
        this.addView(createColon());
        this.addView(mSecondTextView);
    }

    /**
     * 创建冒号
     * 
     * @return
     */
    private TextView createColon() {
        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setText(":");
        return textView;
    }

    /**
     * 创建标签
     * 
     * @return
     */
    private TextView createLabel() {
        TextView textView = new GradientTextView(mContext)
                .setTextColor(getTextColor()).setStrokeColor(getStrokeColor())
                .setBackgroundColor(getBackgroundColor())
                .setTextSize(getTextSize()).setStrokeRadius(getCornerRadius())
                .build();
        return textView;
    }

整体样式已经创建完毕，这里的倒计时是使用CountDownTimer来实现，通过CountDownTimer构造器创建对象时，传入两个值：

  public CountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
   }

millisInFuture：是总的毫秒数 
countDownInterval：间隔毫秒数执行onTick方法

创建CountDownTimer实例时重写以下两个方法：

            @Override
            public void onTick(long millisUntilFinished) {
                setSecond(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                OnCountDownTimerListener.onFinish();
            }

每间隔countDownInterval毫秒执行onTick方法，当倒计时结束后执行onFinish方法。

时、分、秒的计算比较简单，通过除以1000得到总的秒数，将总秒数求余60得到当前的秒；将总秒除以60得到总分，总分求余60的当前的分；总分除以60得总小时，总小时求余24得当前的时，具体公式代码如下：

    /**
     * 设置秒
     * 
     * @param millis
     */
    private void setSecond(long millis) {
        long totalSeconds = millis / 1000;
        String second = (int) (totalSeconds % 60) + "";// 秒
        long totalMinutes = totalSeconds / 60;
        String minute = (int) (totalMinutes % 60) + "";// 分
        long totalHours = totalMinutes / 60;
        String hour = (int) (totalHours % 24) + "";// 时
        Log.i("TAG", "hour:" + hour);
        Log.i("TAG", "minute:" + minute);
        Log.i("TAG", "second:" + second);
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        if (second.length() == 1) {
            second = "0" + second;
        }
        mHourTextView.setText(hour);
        mMinTextView.setText(minute);
        mSecondTextView.setText(second);
    }

以下是完整的BaseCountDownTimerView类的源码：

package com.example.countdowntimerproject.view.base;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseCountDownTimerView extends LinearLayout {

    private Context mContext;

    /**
     * 倒计时控制器
     */
    private CountDownTimer mCountDownTimer;

    private OnCountDownTimerListener OnCountDownTimerListener;

    private int mMillis;

    /**
     * 时
     */
    private TextView mHourTextView;

    /**
     * 分
     */
    private TextView mMinTextView;

    /**
     * 秒
     */
    private TextView mSecondTextView;

    /**
     * 获取边框颜色
     * 
     * @return
     */
    protected abstract String getStrokeColor();

    /**
     * 设置背景色
     * 
     * @return
     */
    protected abstract String getBackgroundColor();

    /**
     * 获取文字颜色
     * 
     * @return
     */
    protected abstract String getTextColor();

    /**
     * 获取边框圆角
     * 
     * @return
     */
    protected abstract int getCornerRadius();

    /**
     * 获取标签文字大小
     * 
     * @return
     */
    protected abstract int getTextSize();

    public BaseCountDownTimerView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public BaseCountDownTimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCountDownTimerView(Context context) {
        this(context, null);
    }

    private void init() {
        this.setOrientation(HORIZONTAL);// 设置布局排列方式
        createView();// 创造三个标签
        addLabelView();// 添加标签到容器中
    }

    /**
     * 创建时、分、秒的标签
     */
    private void createView() {
        mHourTextView = createLabel();
        mMinTextView = createLabel();
        mSecondTextView = createLabel();
    }

    /**
     * 添加标签到容器中
     */
    private void addLabelView() {
        removeAllViews();
        this.addView(mHourTextView);
        this.addView(createColon());
        this.addView(mMinTextView);
        this.addView(createColon());
        this.addView(mSecondTextView);
    }

    /**
     * 创建冒号
     * 
     * @return
     */
    private TextView createColon() {
        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setText(":");
        return textView;
    }

    /**
     * 创建标签
     * 
     * @return
     */
    private TextView createLabel() {
        TextView textView = new GradientTextView(mContext)
                .setTextColor(getTextColor()).setStrokeColor(getStrokeColor())
                .setBackgroundColor(getBackgroundColor())
                .setTextSize(getTextSize()).setStrokeRadius(getCornerRadius())
                .build();
        return textView;
    }

    /**
     * 创建倒计时
     */
    private void createCountDownTimer() {
        mCountDownTimer = new CountDownTimer(mMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                setSecond(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                OnCountDownTimerListener.onFinish();
            }
        };
    }

    /**
     * 设置秒
     * 
     * @param millis
     */
    private void setSecond(long millis) {
        long totalSeconds = millis / 1000;
        String second = (int) (totalSeconds % 60) + "";// 秒
        long totalMinutes = totalSeconds / 60;
        String minute = (int) (totalMinutes % 60) + "";// 分
        long totalHours = totalMinutes / 60;
        String hour = (int) (totalHours % 24) + "";// 时
        Log.i("TAG", "hour:" + hour);
        Log.i("TAG", "minute:" + minute);
        Log.i("TAG", "second:" + second);
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        if (second.length() == 1) {
            second = "0" + second;
        }
        mHourTextView.setText(hour);
        mMinTextView.setText(minute);
        mSecondTextView.setText(second);
    }

    /**
     * 设置监听事件
     * 
     * @param listener
     */
    public void setDownTimerListener(OnCountDownTimerListener listener) {
        this.OnCountDownTimerListener = listener;
    }

    /**
     * 设置时间值
     * 
     * @param millis
     */
    public void setDownTime(int millis) {
        this.mMillis = millis;
    }

    /**
     * 开始倒计时
     */
    public void startDownTimer() {
        createCountDownTimer();// 创建倒计时
        mCountDownTimer.start();
    }

    public void cancelDownTimer() {
        mCountDownTimer.cancel();
    }

