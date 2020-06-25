package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    EditText name,email,password,confirmpassword, age, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.FullName);
        email=findViewById(R.id.Email_ID);
        password=findViewById(R.id.password);
        phone=findViewById(R.id.phone);
        age=findViewById(R.id.age);
        confirmpassword=findViewById(R.id.confirm_password);
        String Name;
        String EmailID;
        String age;
        String phone;
        String Password;
        String ConfirmPassword;
       
        
    }
}
