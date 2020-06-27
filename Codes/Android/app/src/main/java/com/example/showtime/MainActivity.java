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

        //startAnimation();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailIDString = emailID.getText().toString().trim();
                passwordString = password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(emailIDString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                        } else {

                        }

                    }
                });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), SignUp.class));
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
                Log.d("toForgot", "forgot");
                finish();
            }
        });

//
//    private void startAnimation() {
//
//        while (!login.isPressed()) {
//            ImageView reelIcon = (ImageView) findViewById(R.id.animation);
//            float reelIconValue = reelIcon.getY();
//            ObjectAnimator animateXAxis = ObjectAnimator.ofFloat( reelIcon, "x",reelIcon.getX(), reelIcon.getX() + 100.0f, reelIcon.getX() + 300.0f, reelIcon.getX() + 500.0f);
//            ObjectAnimator animateYAxis = ObjectAnimator.ofFloat(reelIcon, "y", 250.0f, 100.0f, 600.0f, 700.0f, 100.0f,200.0f);
//            ObjectAnimator animAlpha = ObjectAnimator.ofFloat(reelIcon, "alpha", 1.0f);
//            animateXAxis.setDuration(40000);
//            animateYAxis.setDuration(50000);
//            animAlpha.setDuration(50000);
//            animateXAxis.start();
//            animateYAxis.start();
//            animAlpha.start();
//
//        }
//    }


    }
}
