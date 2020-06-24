package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
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
    Button login;
    TextView forgotPassword;
    TextView newAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.login_button);
        forgotPassword=findViewById(R.id.forgot_password_text);
        newAccount=findViewById(R.id.sign_up_text);



        startAnimation();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(home);
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        EditText username, password;
        username=findViewById(R.id.userName);
        password=findViewById(R.id.password);
        String regex="^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);


        String name;
        String email_id="";/*fetch from DB*/

        Matcher matcher = pattern.matcher(email_id);
        if(matcher.matches()) {
            String userEnteredPassword=password.getText().toString();;
            String passwordFromDB="";/*fetch from DB*/
            if(passwordFromDB.equals(userEnteredPassword)){

                username.setError(null);
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
            ObjectAnimator animX = ObjectAnimator.ofFloat(
                    reelIcon, "x",
                    reelIcon.getX(),
                    reelIcon.getX() + 100.0f,
                    reelIcon.getX() + 300.0f,
                    reelIcon.getX() + 500.0f);
            ObjectAnimator animY = ObjectAnimator.ofFloat(
                    reelIcon, "y",
                    200.0f, 300.0f, 200.0f, 300.0f, 200.0f,300.0f);
            ObjectAnimator animAlpha = ObjectAnimator.ofFloat(
                    reelIcon, "alpha",
                    1.0f);
            animX.setDuration(10000);
            animY.setDuration(10000);
            animAlpha.setDuration(10000);
            animX.start();
            animY.start();
            animAlpha.start();

        }
    }




}
