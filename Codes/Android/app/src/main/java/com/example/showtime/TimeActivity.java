package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeActivity extends AppCompatActivity {

    TextView movieName;
    Calendar calendar = Calendar.getInstance();


    SimpleDateFormat df = new SimpleDateFormat("dd-MMM", Locale.getDefault());

    Intent toCount;
    Intent i;
    Button date1, date2, date3, date4;
    Button time1, time2, time3, time4;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        toCount = new Intent(getApplicationContext(), TicketCount.class);

        movieName = findViewById(R.id.movieTimingActivity);
        date1 =findViewById(R.id.date1);
        date2 =findViewById(R.id.date2);
        date3 =findViewById(R.id.date3);
        date4 =findViewById(R.id.date4);

        time1=findViewById(R.id.time1);
        time2=findViewById(R.id.time2);
        time3=findViewById(R.id.time3);
        time4=findViewById(R.id.time4);

        Date date = Calendar.getInstance().getTime();
        date1.setText(nextDate(date,1));
        date2.setText(nextDate(date,2));
        date3.setText(nextDate(date,3));
        date4.setText(nextDate(date,4));


        next = findViewById(R.id.nextTimingActivity);

        i = getIntent();
        movieName.setText(i.getStringExtra("movieName"));
    }

    public String nextDate(Date date, int count){
        calendar.setTime(date);
        calendar.add(Calendar.DATE,count);
        Date nextDate = calendar.getTime();
        String formattedDate = df.format(nextDate);
        return formattedDate;
    }



    public void select(View view) {

        switch (view.getId()){

            case R.id.date1:
                date1.setBackgroundResource(R.drawable.activebutton);
                date2.setBackgroundResource(R.drawable.mybutton);
                date4.setBackgroundResource(R.drawable.mybutton);
                date3.setBackgroundResource(R.drawable.mybutton);
                toCount.putExtra("date",date1.getText().toString());
                break;

            case R.id.date2:
                date2.setBackgroundResource(R.drawable.activebutton);
                date1.setBackgroundResource(R.drawable.mybutton);
                date4.setBackgroundResource(R.drawable.mybutton);
                date3.setBackgroundResource(R.drawable.mybutton);
                toCount.putExtra("date",date2.getText().toString());
                break;

            case R.id.date3:
                date3.setBackgroundResource(R.drawable.activebutton);
                date1.setBackgroundResource(R.drawable.mybutton);
                date4.setBackgroundResource(R.drawable.mybutton);
                date2.setBackgroundResource(R.drawable.mybutton);
                toCount.putExtra("date",date3.getText().toString());
                break;

            case R.id.date4:
                date4.setBackgroundResource(R.drawable.activebutton);
                date1.setBackgroundResource(R.drawable.mybutton);
                date2.setBackgroundResource(R.drawable.mybutton);
                date3.setBackgroundResource(R.drawable.mybutton);
                toCount.putExtra("date",date4.getText().toString());
                break;

            case R.id.time1:
                time1.setBackgroundResource(R.drawable.activebutton);
                time2.setBackgroundResource(R.drawable.mybutton);
                time3.setBackgroundResource(R.drawable.mybutton);
                time4.setBackgroundResource(R.drawable.mybutton);
                toCount.putExtra("time",time1.getText().toString());
                break;

            case R.id.time2:
                time2.setBackgroundResource(R.drawable.activebutton);
                time1.setBackgroundResource(R.drawable.mybutton);
                time3.setBackgroundResource(R.drawable.mybutton);
                time4.setBackgroundResource(R.drawable.mybutton);
                toCount.putExtra("time",time2.getText().toString());
                break;

            case R.id.time3:
                time3.setBackgroundResource(R.drawable.activebutton);
                time2.setBackgroundResource(R.drawable.mybutton);
                time1.setBackgroundResource(R.drawable.mybutton);
                time4.setBackgroundResource(R.drawable.mybutton);
                toCount.putExtra("time",time3.getText().toString());
                break;

            case R.id.time4:
                time4.setBackgroundResource(R.drawable.activebutton);
                time2.setBackgroundResource(R.drawable.mybutton);
                time3.setBackgroundResource(R.drawable.mybutton);
                time1.setBackgroundResource(R.drawable.mybutton);
                toCount.putExtra("time",time4.getText().toString());
                break;

            case R.id.nextTimingActivity:
                toCount.putExtra("movieName",movieName.getText().toString());
                toCount.putExtra("theater", i.getStringExtra("theater"));
                toCount.putExtra("location", i.getStringExtra("location"));
                startActivity(toCount);
        }
    }
}