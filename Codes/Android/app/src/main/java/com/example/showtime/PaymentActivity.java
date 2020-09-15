package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentActivity extends AppCompatActivity {

    EditText cardNumberET, nameOnCardET;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Spinner monthSp, yearSp;
    Button addPayment;
    String[] monthArray = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    String[] yearArray = {"2020","2021","2022","2023","2024","2025","2026"};
    String month="", year="";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cardNumberET = findViewById(R.id.CardNumber);
        nameOnCardET = findViewById(R.id.NameOnCard);
        monthSp = findViewById(R.id.SpinnerMonth);
        yearSp = findViewById(R.id.SpinnerYear);
        addPayment = findViewById(R.id.AddPayment);

        ArrayAdapter monthAdapter =new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, monthArray);
        monthSp.setAdapter(monthAdapter);
        monthSp.setOnItemSelectedListener(this);

        ArrayAdapter yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yearArray);
        yearSp.setAdapter(yearAdapter);
        yearSp.setOnItemSelectedListener(this);
    }
}