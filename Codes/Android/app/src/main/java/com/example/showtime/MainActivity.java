package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    /**
     * Button for login
     */
    Button login, register;
    TextView forgotPassword;
    EditText emailID, password;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String emailIDString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login_button);
        register = findViewById(R.id.register_button_Login);
        forgotPassword = findViewById(R.id.forgot_password_text);
        emailID = findViewById(R.id.emailID_Login);
        password = findViewById(R.id.password_Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailIDString = emailID.getText().toString().trim();
                passwordString = password.getText().toString().trim();

                if(emailIDString.isEmpty() && passwordString.isEmpty()){
                    emailID.setError("Enter the email ID");
                    password.setError("Enter the password");
                }
                else if(emailIDString.isEmpty()){
                    emailID.setError("Enter the email ID");
                }
                else if(passwordString.isEmpty()){
                    password.setError("Enter the password");
                }
                else if(emailIDString.equals("admin") && passwordString.equals("admin")){

                    Toast.makeText(MainActivity.this, "Logged in Successfully as admin", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Admin.class));

                }
                else {
                    mAuth.signInWithEmailAndPassword(emailIDString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(MainActivity.this, "Logged in Successfully as customer", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                                } else {
                                    Toast.makeText(MainActivity.this, "Verify your Email address", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Wrong credentials" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
                Log.d("toForgot", "forgot");
            }
        });

    }
}
