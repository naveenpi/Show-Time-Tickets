package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ViewTicket extends AppCompatActivity {

    private static final String CHANNEL_ID = "ticket";
    ImageView img;
    TextView text;
    Button save;
    Intent i = getIntent();
    String msg ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ticket);
        Toast.makeText(getApplicationContext(),"Movie booked",Toast.LENGTH_LONG).show();
        i=getIntent();
        img = findViewById(R.id.image_Ticket);
        text = findViewById(R.id.Seats_Ticket);
        save = findViewById(R.id.button_save);
        msg=i.getStringExtra("details");
        //Log.d("details",i.getStringExtra("details"));
        //System.out.println(i.getStringExtra("details"));

        Picasso.get().load(i.getStringExtra("img")).into(img);
        text.setText(i.getStringExtra("details"));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail();
                addNotification();
                addToHistory();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });

    }

    private void addToHistory() {

        DocumentReference productIdRef = FirebaseFirestore.getInstance()
                .collection("booking")
                .document(i.getStringExtra("id"));

        productIdRef
                .update("payment","yes")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("update success", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("update fail", "Error updating document", e);
                    }
                });
        //productIdRef.delete();
    }

    private void sendEmail() {
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,FirebaseAuth.getInstance().getCurrentUser().getEmail(),"Tickets Purchased",msg);
        javaMailAPI.execute();

    }

    private void addNotification() {

        Intent notificationIntent = new Intent(ViewTicket. this, HomeActivity. class );
        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
        notificationIntent.setAction(Intent. ACTION_MAIN ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
        PendingIntent resultIntent = PendingIntent. getActivity (ViewTicket. this, 0 , notificationIntent , 0 ) ;


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle("Show Time Tickets")
                .setContentText("Confirmed tickets")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("completed the payment successfully"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Seating channel";
            String description = "channel for adding to the cart";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d("new notification","Notification");
            notificationManager.notify(100,builder.build());
        }


    }
    @Override
    public void onBackPressed() {

    }
}