package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SeatingActivity extends AppCompatActivity implements View.OnClickListener {

    TableLayout seats_tableLayout;
    int actual_seats_selected = 0;
    int no_of_seats = 0;
    String seats_selected_string = "";
    AlertDialog.Builder display_alert;
    Button seats_proceed;
    ArrayList<String> EXISTS = new ArrayList<String>();
    Intent i;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String seatsFromDB="", img = "";
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seating);

        seats_proceed = findViewById(R.id.seats_proceed);
        i = getIntent();
        no_of_seats = i.getIntExtra("count", 0);
        seats_tableLayout = findViewById(R.id.table_layout);
        seats_tableLayout.setOnClickListener(this);
        seats_proceed.setOnClickListener(this);

        query =  firebaseFirestore.collection("booking")
                .whereEqualTo("movieName",i.getStringExtra("movieName"))
                .whereEqualTo("date",i.getStringExtra("date"))
                .whereEqualTo("time",i.getStringExtra("time"));

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("booking query success", document.getId() + " => " + document.getString("seats"));
                        seatsFromDB = document.getString("seats");
                        String[] arrOfStr = seatsFromDB.split(",");
                        for (String str : arrOfStr) {

                            TextView textView = findViewById(getResources().getIdentifier(str, "id", getPackageName()));
                            textView.setBackgroundResource(R.drawable.fill);
                            textView.setEnabled(false);
                        }
                    }
                }
                else {
                    Log.d("booking query fail", "Error getting documents: ", task.getException());
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

        Drawable drawable = v.getResources().getDrawable(R.drawable.fill);
        if(v.getBackground().equals(drawable) ){
            Log.d("red","already selected");
            onFilledClick(v);
        }
        else {
            if (!(v.getId() == seats_proceed.getId())) {
                String seat_no = v.getTag().toString();
                if (EXISTS.contains(seat_no)) {
                    v.setBackgroundResource(R.drawable.empty);
                    if (actual_seats_selected > 0) {
                        actual_seats_selected -= 1;
                    } else {
                        actual_seats_selected = 0;
                    }
                    EXISTS.remove(seat_no);
                    return;
                }
                if (actual_seats_selected < no_of_seats) {
                    v.setBackgroundResource(R.drawable.select);
                    seats_selected_string = "";
                    actual_seats_selected += 1;
                    EXISTS.add(seat_no);
                    Iterator itr = EXISTS.iterator();
                    while (itr.hasNext()) {
                        seats_selected_string += itr.next() + ",";
                    }
                } else {
                    display_alert = new AlertDialog.Builder(SeatingActivity.this);
                    display_alert.setTitle("Error");
                    display_alert.setMessage("You have selected maximum number of seats ");
                    display_alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            display_alert.create().dismiss();
                        }
                    });
                    display_alert.show();
                }
            } else if (v.getId() == seats_proceed.getId()) {
                if (actual_seats_selected == no_of_seats) {

                    String[] arrOfStr = seats_selected_string.split(",");
                    for (String str : arrOfStr) {

                        TextView textView = findViewById(getResources().getIdentifier(str, "id", getPackageName()));
                        textView.setBackgroundResource(R.drawable.fill);
                        textView.setEnabled(false);
                    }
                    Log.d("Ticket", i.getStringExtra("movieName") + ", " + i.getStringExtra("date") + ", " + i.getStringExtra("time") + ", " + i.getIntExtra("count", 1) + ", " + removeLastChar(seats_selected_string));

                    final Map<String,Object> booking = new HashMap<>();
                    booking.put("user",mAuth.getCurrentUser().getUid());
                    booking.put("movieName",i.getStringExtra("movieName"));
                    booking.put("date",i.getStringExtra("date"));
                    booking.put("time",i.getStringExtra("time"));
                    booking.put("seats",removeLastChar(seats_selected_string));

                    db.collection("booking").add(booking).
                            addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("Yes booking", " booking confirmed ");
                                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("No booking", " booking not confirmed ");
                        }
                    });


                } else {
                    display_alert = new AlertDialog.Builder(SeatingActivity.this);
                    display_alert.setTitle("Error");
                    display_alert.setMessage("Please select all the opted number of seats ");
                    display_alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            display_alert.create().dismiss();
                        }
                    });
                    display_alert.show();
                }
            }
        }
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public void onFilledClick(View view) {
        display_alert = new AlertDialog.Builder(SeatingActivity.this);
        display_alert.setTitle("Error");
        display_alert.setMessage("Please select another one this seat is already filled.");
        display_alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                display_alert.create().dismiss();
            }
        });
        display_alert.show();
    }
}