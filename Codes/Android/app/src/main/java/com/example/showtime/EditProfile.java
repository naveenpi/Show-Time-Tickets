package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.api.Authentication;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {

    EditText fullName_EditText,age_EditText, phone_EditText, email_EditText;
    Button register_RegistrationActivity;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fullNameString,phoneRegisterString, ageString, emailRegisterString;
    String customerID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        fullName_EditText=findViewById(R.id.FullNameEdit);
        age_EditText=findViewById(R.id.ageEdit);
        phone_EditText=findViewById(R.id.phoneEdit);
        email_EditText=findViewById(R.id.Email_ID_RegistrationEdit);
        register_RegistrationActivity=findViewById(R.id.registerButtonRegistrationActivityEdit);
        email_EditText.setText(mAuth.getCurrentUser().getEmail());
        email_EditText.setEnabled(false);

        register_RegistrationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullNameString=fullName_EditText.getText().toString();
                phoneRegisterString=phone_EditText.getText().toString();
                ageString=age_EditText.getText().toString();

                if(validations()){
                    customerID=mAuth.getCurrentUser().getUid();

                    mAuth.sendPasswordResetEmail(mAuth.getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(getApplicationContext(),"Password reset link is sent to the given Email Id",Toast.LENGTH_SHORT);

                                DocumentReference documentReference= db.collection("customers").document(customerID);
                                Map<String,Object> customer= new HashMap<>();
                                customer.put("FullName",fullNameString);
                                customer.put("EmailID",mAuth.getCurrentUser().getEmail());
                                customer.put("PhoneNumber",phoneRegisterString);
                                customer.put("Age",ageString);
                                documentReference.update(customer)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("profile changed", "Successfully changed profile "+ customerID);
                                                mAuth.signOut();
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("profile not changed", "Failed to change profile"+ customerID);
                                    }
                                });

                                Intent toHome = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(toHome);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validations() {

        if(fullName_EditText.getText().toString().isEmpty()){
            fullName_EditText.setError("Enter the Full Name");
            return false;
        }

        if(age_EditText.getText().toString().isEmpty()){
            age_EditText.setError("Enter Age");
            return false;
        }
        if(phone_EditText.getText().toString().isEmpty()){
            phone_EditText.setError("Enter Phone Number");
            return false;
        }
//        if(password_EditText.getText().toString().isEmpty()){
//            password_EditText.setError("Enter password");
//            return false;
//        }
//        if(confirmPassword_EditText.getText().toString().isEmpty()){
//            confirmPassword_EditText.setError("Enter confirm password");
//            return false;
//        }
//        if(!passwordRegisterString.equals(confirmPasswordRegisterString)){
//            Toast.makeText(EditProfile.this,"Password does not match",Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }
}