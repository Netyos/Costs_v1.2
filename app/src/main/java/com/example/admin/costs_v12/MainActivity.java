package com.example.admin.costs_v12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

import java.util.Calendar;

/*
 * Displays a calendar and buttons to move to another activities.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    public static String sDate;
    public static String sMonth;
    Button btnMonthlyCosts, btnCategories;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnMonthlyCosts = (Button) findViewById(R.id.btnMonthlyCosts);
        btnMonthlyCosts.setOnClickListener(this);

        btnCategories = (Button) findViewById(R.id.btnCategories);
        btnCategories.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        int mCurrentYear = c.get(Calendar.YEAR);
        int mCurrentMonth = c.get(Calendar.MONTH);
        int mCurrentDay = c.get(Calendar.DAY_OF_MONTH);

        sDate = String.valueOf(mCurrentDay) + "-" + (mCurrentMonth + 1) + "-" + mCurrentYear + " ";
        sMonth = String.valueOf(mCurrentMonth + 1) + "-" + mCurrentYear + " ";

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {

            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                sDate = String.valueOf(dayOfMonth) + "-" + (month + 1) + "-" + year + " ";
                sMonth = String.valueOf(month + 1) + "-" + year + " ";
            }
        });
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnMonthlyCosts:
                Intent intentMontlyCosts = new Intent(getApplicationContext(), MonthlyCosts.class);
                startActivity(intentMontlyCosts);
                break;

            case R.id.btnCategories:
                Intent intentCategories = new Intent(getApplicationContext(), Categories.class);
                startActivity(intentCategories);
                break;
        }
    }
}
