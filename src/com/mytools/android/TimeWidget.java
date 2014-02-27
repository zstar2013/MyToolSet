package com.mytools.android;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class TimeWidget extends TextView {
	 
    Calendar mCalendar;
    private final static String m12 = "h:mm:ss";
    private final static String m24 = "k:mm:ss";
    private FormatChangeObserver mFormatChangeObserver;

    private Runnable mTicker;
    private Handler mHandler;

    private boolean mTickerStopped = false;

    String mFormat;

    private static String[] weekdays = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };

    public TimeWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initClock(context);
    }

    public TimeWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClock(context);
    }

    public TimeWidget(Context context) {
        super(context);
        initClock(context);
    }

    private void initClock(Context context) {
        Resources r = context.getResources();

        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }

        mFormatChangeObserver = new FormatChangeObserver();
        //ע�ᣬ����ϵͳ�����������ݿ�ĸı�
        getContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, mFormatChangeObserver);

        setFormat();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }

    /**
     * Pulls 12/24 mode from system settings
*/
    private boolean get24HourMode() {
        return android.text.format.DateFormat.is24HourFormat(getContext());
    }

    private void setFormat() {
        if (get24HourMode()) {
            mFormat = m24;
        } else {
            mFormat = m12;
        }
    }

    // format the string of time
    private static String format(int t) {
        String s = "" + t;
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("", "TimeWidget.onTouchEvent");

        getContext().startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));

        return super.onTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.i("", "TimeWidget.onAttachedToWindow");
        mTickerStopped = false;
        super.onAttachedToWindow();
        mHandler = new Handler();

        /**
         * requests a tick on the next hard-second boundary
*/
        mTicker = new Runnable() {
            public void run() {
                if (mTickerStopped)
                    return;
                mCalendar.setTimeInMillis(System.currentTimeMillis());

                int myear = (mCalendar.get(Calendar.YEAR));
                int mmonth = (mCalendar.get(Calendar.MONTH) + 1);// �·�+1��һ���еĵڼ�����
                int mmonthday = (mCalendar.get(Calendar.DAY_OF_MONTH));// һ���е�����
                final int mweekday = (mCalendar.get(Calendar.DAY_OF_WEEK)) - 1;

                final String mDate = format(myear) + "-" + format(mmonth) + "-" + format(mmonthday);

                setText(DateFormat.format(mFormat, mCalendar) + "  " + mDate + " " + weekdays[mweekday]);
                invalidate();
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                mHandler.postAtTime(mTicker, next);
            }
        };
        mTicker.run();
    }

    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange) {
            setFormat();
        }
    }

}
