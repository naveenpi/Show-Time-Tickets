package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MovieDetails extends AppCompatActivity {

    TextView movieNameTextView,genreTextView, ratingTextView, priceTextView;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieNameTextView=findViewById(R.id.textView9);
        genreTextView=findViewById(R.id.textView10);
        ratingTextView=findViewById(R.id.textView11);
        priceTextView=findViewById(R.id.textView12);

//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//
//        String movieId=getIntent().getStringExtra("movieId");
//        DocumentReference documentReference=db.collection("movie").document(movieId);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//
//
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//
//                if (e != null) {
//                    Log.w("fail", "Listen failed.", e);
//                    return;
//                }
//
//                if(documentSnapshot!=null && documentSnapshot.exists()) {
//                    movieNameTextView.setText("Movie Name: "+documentSnapshot.getString("Movie Name"));
//                    genreTextView.setText("Genre: "+documentSnapshot.getString("Genre"));
//                    ratingTextView.setText("Rating: "+documentSnapshot.getString("Rating"));
//                    priceTextView.setText("Ticket Price: "+documentSnapshot.getString("Ticket Price"));
//                    Log.d("exists", "onEvent: Document do not exists");
//                }
//                else{
//                    Log.d("not", "onEvent: Document do not exists");
//                }
//            }
//        });

    }
}