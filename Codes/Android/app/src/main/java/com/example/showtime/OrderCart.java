package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderCart extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderAdapter adapter;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    AlertDialog.Builder display_alert;
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM", Locale.getDefault());
    SimpleDateFormat dt = new SimpleDateFormat("hh:mm a");
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);
        recyclerView = findViewById(R.id.recyclerViewOrderList);
        constructRecyclerView();
    }

    private void constructRecyclerView() {

        Date date = Calendar.getInstance().getTime();

        Query query = firebaseFirestore.collection("booking").whereEqualTo("user",mAuth.getCurrentUser().getUid())
                .whereGreaterThanOrEqualTo("date",df.format(calendar.getTime()))
                .whereEqualTo("payment","no");

        Log.d("booking query",query.toString());
        FirestoreRecyclerOptions<OrderModel> response = new FirestoreRecyclerOptions.Builder<OrderModel>()
                .setQuery(query, OrderModel.class)
                .build();

        adapter = new OrderAdapter(response);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                display_alert = new AlertDialog.Builder(OrderCart.this);
                display_alert.setTitle("DELETE");
                display_alert.setMessage("Are You sure? ");
                display_alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.deleteItem(viewHolder.getAdapterPosition());
                        display_alert.create().dismiss();
                    }
                });
                display_alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        display_alert.create().dismiss();
                        recyclerView.setAdapter(adapter);

                    }
                });
                display_alert.show();

            }

        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {

            @Override
            public void clickOnItem(DocumentSnapshot documentSnapshot, int position) {
                Intent i = new Intent(getApplicationContext(),CheckOut.class);

                Log.d("intent ",documentSnapshot.get("movieName").toString());
                i.putExtra("id",documentSnapshot.getId());
                i.putExtra("movieName",documentSnapshot.get("movieName").toString());
                i.putExtra("dateTimeSeats","Date: "+documentSnapshot.get("date").toString()+"\nTime: "+documentSnapshot.get("time")+"\nSeats: "+documentSnapshot.get("seats")+"\nTheater: "+documentSnapshot.get("theater")+"\nLocation: "+documentSnapshot.get("location"));
                i.putExtra("seats",documentSnapshot.get("seats")+"");
                startActivity(i);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}