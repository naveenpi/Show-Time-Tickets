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

public class CheckHistory extends AppCompatActivity {

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
        setContentView(R.layout.activity_check_history);
        recyclerView = findViewById(R.id.recyclerViewOrderListHistory);
        constructRecyclerView();
    }

    private void constructRecyclerView() {

        Date date = Calendar.getInstance().getTime();

        Query query = firebaseFirestore.collection("booking").whereEqualTo("user",mAuth.getCurrentUser().getUid())
                .whereEqualTo("payment","yes");

        Log.d("booking query",query.toString());
        FirestoreRecyclerOptions<OrderModel> response = new FirestoreRecyclerOptions.Builder<OrderModel>()
                .setQuery(query, OrderModel.class)
                .build();

        adapter = new OrderAdapter(response);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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