package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TicketCount extends AppCompatActivity {

    Button selectedSeats;
    RadioGroup radioGroup;
    Intent i;
    int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_count);

        selectedSeats = findViewById(R.id.selected_seats);
        radioGroup = findViewById(R.id.seats);
        i = getIntent();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton =findViewById(checkedId);
                count =Integer.parseInt(radioButton.getText().toString());

            }
        });

        selectedSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSeating = new Intent(getApplicationContext(), SeatingActivity.class);
                toSeating.putExtra("movieName",i.getStringExtra("movieName"));
                toSeating.putExtra("theater", i.getStringExtra("theater"));
                toSeating.putExtra("location", i.getStringExtra("location"));
                toSeating.putExtra("date",i.getStringExtra("date"));
                toSeating.putExtra("time",i.getStringExtra("time"));
                toSeating.putExtra("count",count);
                Log.d("seats count:",count+"");
                startActivity(toSeating);
            }
        });
    }
}