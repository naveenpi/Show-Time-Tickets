package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    Button save;
    RatingBar bar;
    EditText feedbackText;
    TextView display;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        save = findViewById(R.id.feedbackButton);
        bar = findViewById(R.id.ratingBar);
        display = findViewById(R.id.feedbackTextView);
        feedbackText = findViewById(R.id.feedbackEditText);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = "Rating is :" + bar.getRating();

                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();

                Map<String,Object> feedbackRating= new HashMap<>();
                feedbackRating.put("feedback",bar.getRating());
                feedbackRating.put("review",feedbackText.getText().toString());
                db.collection("feedback")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .set(feedbackRating)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Added feedback",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed to add feedback",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}