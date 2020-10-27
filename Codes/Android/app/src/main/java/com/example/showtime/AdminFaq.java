package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminFaq extends AppCompatActivity {

    EditText question, answer;
    Button post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_faq);

        question = findViewById(R.id.question_admin);
        answer = findViewById(R.id.answer_admin);
        post = findViewById(R.id.post_admin);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> faq_pair = new HashMap<>();
                faq_pair.put("question",question.getText().toString());
                faq_pair.put("answer",answer.getText().toString());

                FirebaseFirestore.getInstance().collection("Faq")
                        .add(faq_pair)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("yes faq", " faq is added ");
                                Toast.makeText(getApplicationContext(),"Added FAQ",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),Admin.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("No faq", " faq is not added");
                            }
                        });
            }
        });
    }
}