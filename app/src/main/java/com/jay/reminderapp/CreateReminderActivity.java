package com.jay.reminderapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateReminderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText date;
    private EditText time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        setTitle("");
        if (this.getSupportActionBar()!=null) {
            this.getSupportActionBar().hide();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        Toolbar toolbarReminderTextview = findViewById(R.id.toolbar_reminder_textview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0.0f);
            toolbarReminderTextview.setElevation(0.0f);
        }

        date = findViewById(R.id.edittext_date);
        time = findViewById(R.id.edittext_time);
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        onDateSet(null,mYear,mMonth,mDay);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(CreateReminderActivity.this, CreateReminderActivity.this, mYear, mMonth, mDay);
        // Get Current Time
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        onTimeSet(null,mHour,mMinute);
        final TimePickerDialog timePickerDialog = new TimePickerDialog(CreateReminderActivity.this, CreateReminderActivity.this, mHour, mMinute, false);
        date.setRawInputType(InputType.TYPE_NULL);
//        date.setFocusable(true);
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                datePickerDialog.show();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        time.setRawInputType(InputType.TYPE_NULL);
//        time.setFocusable(true);
        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                timePickerDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_reminder_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date date1 = new Date(year, month, dayOfMonth-1);
        String dayOfWeek = simpledateformat.format(date1);
        date.setText(dayOfWeek+", "+dayOfMonth + "-" + (month + 1) + "-" + year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(String.valueOf(hourOfDay + ":" + minute));
            time.setText(_12HourSDF.format(_24HourDt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
