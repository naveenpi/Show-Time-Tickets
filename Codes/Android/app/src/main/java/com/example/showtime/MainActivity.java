package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    /**
     * Button for login
     */
    Button login, register;
    TextView forgotPassword;
    EditText emailID, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.login_button);
        register=findViewById(R.id.register_button_Login);
        forgotPassword=findViewById(R.id.forgot_password_text);
        emailID=findViewById(R.id.emailID_Login);
        password=findViewById(R.id.password_Login);

        startAnimation();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailID.getText().toString().isEmpty()){
                    emailID.setError("Please type the username");
                }
                if(password.getText().toString().isEmpty()){
                    password.setError("Please type the password");
                }
                Intent home=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(home);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),SignUp.class));
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
                Log.d("toForgot","forgot");
                finish();
            }
        });




        String regex="^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);


        String name;
        String email_id="";/*fetch from DB*/

        Matcher matcher = pattern.matcher(email_id);
        if(matcher.matches()) {
            String userEnteredPassword=password.getText().toString();;
            String passwordFromDB="";/*fetch from DB*/
            if(passwordFromDB.equals(userEnteredPassword)){

                emailID.setError(null);
                Intent toHome = new Intent(getApplicationContext(),HomeActivity.class);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "wrong credentials", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Incorrect user name", Toast.LENGTH_SHORT).show();
        }

    }

    private void startAnimation() {

        while (!login.isPressed()) {
            ImageView reelIcon = (ImageView) findViewById(R.id.animation);
            float reelIconValue = reelIcon.getY();
            ObjectAnimator animateXAxis = ObjectAnimator.ofFloat( reelIcon, "x",reelIcon.getX(), reelIcon.getX() + 100.0f, reelIcon.getX() + 300.0f, reelIcon.getX() + 500.0f);
            ObjectAnimator animateYAxis = ObjectAnimator.ofFloat(reelIcon, "y", 250.0f, 100.0f, 600.0f, 700.0f, 100.0f,200.0f);
            ObjectAnimator animAlpha = ObjectAnimator.ofFloat(reelIcon, "alpha", 1.0f);
            animateXAxis.setDuration(40000);
            animateYAxis.setDuration(50000);
            animAlpha.setDuration(50000);
            animateXAxis.start();
            animateYAxis.start();
            animAlpha.start();

        }
    }




}
