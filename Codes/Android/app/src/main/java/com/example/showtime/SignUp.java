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

        if(name.getText().toString().isEmpty()){
            name.setError("Enter the Name");
        }
        if(email.getText().toString().isEmpty()){
            email.setError("Enter the Email");
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Enter the password");
        }
        if(confirmpassword.getText().toString().isEmpty()){
            confirmpassword.setError("Enter the password again");
        }
        if(phone.getText().toString().isEmpty()){
            phone.setError("Enter the phone number");
        }
        if(age.getText().toString().isEmpty()){
            age.setError("Enter the age");
        }

//        String Name;
//        String EmailID;
//        String age;
//        String phone;
//        String Password;
//        String ConfirmPassword;

        
    }
}
