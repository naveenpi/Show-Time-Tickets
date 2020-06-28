package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    TextView titleForgotPassword,toLogin;
    EditText emailID_ForgotPassword_EditText;
    Button resetButton;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        titleForgotPassword=findViewById(R.id.textView_Forgot_Password);
        emailID_ForgotPassword_EditText=findViewById(R.id.Email_ID_Forgot_Password);
        resetButton=findViewById(R.id.Reset_Forgot_Password);
        toLogin=findViewById(R.id.toLogin);
    }
}