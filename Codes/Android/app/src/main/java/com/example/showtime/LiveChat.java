package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LiveChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_chat);
    }
    if(FirebaseAuth.getInstance().getCurrentUser() == null) {
    // Start sign in/sign up activity
    startActivityForResult(
            AuthUI.getInstance()
            .createSignInIntentBuilder()
            .build(),
            SIGN_IN_REQUEST_CODE
    );
} else {
    // User is already signed in. Therefore, display
    // a welcome Toast
    Toast.makeText(this,
            "Welcome " + FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .getDisplayName(),
            Toast.LENGTH_LONG)
            .show();
 
    // Load chat room contents
    displayChatMessages();
}
}
