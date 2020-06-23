package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    /**
     * Button for login
     */
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.login_button);
        startAnimation();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //updateUI(currentUser);
    }

}
