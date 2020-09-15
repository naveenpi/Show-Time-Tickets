package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String,Object> payments= new HashMap<>();
                payments.put("user",mAuth.getCurrentUser().getUid());
                payments.put("cardNumber",cardNumberET.getText().toString());
                payments.put("nameOnCard",nameOnCardET.getText().toString());
                payments.put("month",month);
                payments.put("year",year);

                db.collection("payments").add(payments)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("Yes card", " Card is added ");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("No card", " Card is not added");
                            }
                        });


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner monthSpin = (Spinner) parent;
        Spinner yearSpin = (Spinner) parent;

        if(monthSpin.getId() == R.id.SpinnerMonth){
            month = monthArray[position];
        }
        if(yearSpin.getId() == R.id.SpinnerYear){
            year = yearArray[position];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}