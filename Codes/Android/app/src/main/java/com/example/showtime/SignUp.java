package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText fullName_EditText,email_EditText,password_EditText,confirmPassword_EditText, age_EditText, phone_EditText;
    Button register_RegistrationActivity;
    TextView login_Registration_Text;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fullNameString,emailRegisterString, passwordRegisterString, confirmPasswordRegisterString, phoneRegisterString, ageString;
    String customerID;
    String emailIDRegex="^(.+)@(.+)$";
    Pattern pattern = Pattern.compile(emailIDRegex);
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName_EditText=findViewById(R.id.FullName);
        email_EditText=findViewById(R.id.Email_ID_Registration);
        password_EditText=findViewById(R.id.passwordRegistration);
        confirmPassword_EditText=findViewById(R.id.confirmPasswordRegistration);
        age_EditText=findViewById(R.id.age);
        phone_EditText=findViewById(R.id.phone);
        register_RegistrationActivity=findViewById(R.id.registerButtonRegistrationActivity);
        login_Registration_Text=findViewById(R.id.loginRegistration);

        mAuth = FirebaseAuth.getInstance();

        register_RegistrationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullNameString=fullName_EditText.getText().toString();
                emailRegisterString=email_EditText.getText().toString().trim();
                passwordRegisterString=password_EditText.getText().toString().trim();
                confirmPasswordRegisterString=confirmPassword_EditText.getText().toString().trim();
                phoneRegisterString=phone_EditText.getText().toString();
                ageString=age_EditText.getText().toString();

                if(validations()) {
                }}
        });
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

        String Name;
        String EmailID;
        String age;
        String phone;
        String Password;
        String ConfirmPassword;

        
    }
}
