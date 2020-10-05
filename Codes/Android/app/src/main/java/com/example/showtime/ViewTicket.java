package com.example.showtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ViewTicket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ticket);
    }

    DocumentReference docRef = db.collection("movies").document("amal");

    // Source can be CACHE, SERVER, or DEFAULT.
    Source source = Source.CACHE;

// Get the document, forcing the SDK to use the offline cache
docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                // Document found in the offline cache
                DocumentSnapshot document = task.getResult();
                Log.d(TAG, "Cached document data: " + document.getData());
            } else {
                Log.d(TAG, "Cached get failed: ", task.getException());
            }
        }
    });
}