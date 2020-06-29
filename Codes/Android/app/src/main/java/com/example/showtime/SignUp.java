package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
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

                if(validations()){
                    mAuth.createUserWithEmailAndPassword(emailRegisterString,passwordRegisterString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                customerID=mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference= db.collection("customers").document(customerID);
                                Map<String,Object> customer= new HashMap<>();
                                customer.put("FullName",fullNameString);
                                customer.put("EmailID",emailRegisterString);
                                customer.put("PhoneNumber",phoneRegisterString);
                                customer.put("Age",ageString);
                                documentReference.set(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Log.d("Successful registration", "user Profile is created for "+ customerID);
                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Failure registration", "user Profile is not created"+ customerID);
                                    }
                                });
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "A Verification Email has been sent to the registered Email.", Toast.LENGTH_SHORT).show();
                                            Log.d("verification Email", "Email sent.");
                                        }
                                        else{
                                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(SignUp.this, "An error has occurred."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

        login_Registration_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        
    }

    private boolean validations() {

        if(fullName_EditText.getText().toString().isEmpty()){
            fullName_EditText.setError("Enter the Full Name");
            return false;
        }
        if(email_EditText.getText().toString().isEmpty()){
            email_EditText.setError("Enter the E-mail address");
            return false;
        }
        matcher = pattern.matcher(emailRegisterString);

        if(age_EditText.getText().toString().isEmpty()){
            age_EditText.setError("Enter Age");
            return false;
        }
        if(phone_EditText.getText().toString().isEmpty()){
            phone_EditText.setError("Enter Phone Number");
            return false;
        }
        if(password_EditText.getText().toString().isEmpty()){
            password_EditText.setError("Enter password");
            return false;
        }
        if(confirmPassword_EditText.getText().toString().isEmpty()){
            confirmPassword_EditText.setError("Enter confirm password");
            return false;
        }
        if(!passwordRegisterString.equals(confirmPasswordRegisterString)){
            Toast.makeText(SignUp.this,"Password does not match",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!matcher.matches()){
            email_EditText.setError("Invalid Email address");
        }
        return true;
    }
}
