package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class CheckOut extends AppCompatActivity {

    TextView movieName, genre, rating, ticketPrice, details;
    ImageView moviePoster;
    String moviePosterString="";
    Button checkOut;
    Intent i;
    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
//        movieName = findViewById(R.id.MovieName_List_Check_out);
//        genre = findViewById(R.id.Genre_List_Check_out);
//        rating = findViewById(R.id.Rating_List_Check_out);
//        ticketPrice = findViewById(R.id.TicketPrice_List_Check_out);
        moviePoster = findViewById(R.id.image_Check_Out);
        details = findViewById(R.id.Seats_check_out);
        checkOut = findViewById(R.id.button_check_out);

        i = getIntent();
        String s = i.getStringExtra("seats");
        final String[] str=s.split(",");

        DocumentReference ref = FirebaseFirestore.getInstance().collection("movie")
                .document(i.getStringExtra("movieName"));
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    moviePosterString = doc.getString("imgReference");
                    Picasso.get().load(doc.getString("imgReference")).into(moviePoster);
//                    movieName.setText("Movie Name: "+doc.getString("movieName"));
//                    genre.setText("Genre: "+doc.getString("genre"));
//                    rating.setText("Rating: "+doc.getString("rating"));
//                    ticketPrice.setText("Total Price: $"+"567");
                    msg = "Movie Name: "+doc.getString("movieName")+"\n"+
                            "Genre: "+doc.getString("genre")+"\n"+
                            "Rating: "+doc.getString("rating")+"\n"+
                            "Total Tickets: "+str.length+"\n"+
                            "Total Price: $"+str.length*Double.parseDouble(doc.getString("ticketPrice"))+"\n"+
                            i.getStringExtra("dateTimeSeats");
                    details.setText(msg);
                }
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCheckOutPayments = new Intent(getApplicationContext(),CheckOutPayments.class);
                toCheckOutPayments.putExtra("id",i.getStringExtra("id"));
                toCheckOutPayments.putExtra("details",msg);
                toCheckOutPayments.putExtra("img",moviePosterString);
                startActivity(toCheckOutPayments);
            }
        });


    }
}