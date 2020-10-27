package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CheckOutPayments extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    PaymentsAdapter adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_payments);
        recyclerView = findViewById(R.id.recyclerViewListPaymentsCheckOut);
        constructRecyclerView();
    }

    private void constructRecyclerView() {

        Query query = firebaseFirestore.collection("payments").whereEqualTo("user",mAuth.getCurrentUser().getUid());
        Log.d("payment query",query.toString());
        FirestoreRecyclerOptions<PaymentsModel> response = new FirestoreRecyclerOptions.Builder<PaymentsModel>()
                .setQuery(query, PaymentsModel.class)
                .build();

        adapter = new PaymentsAdapter(response);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }

        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new PaymentsAdapter.OnItemClickListener() {
            @Override
            public void clickOnItem(DocumentSnapshot documentSnapshot, int position) {

                Intent i = getIntent();
                Intent toTicket = new Intent(getApplicationContext(), ViewTicket.class);
                toTicket.putExtra("id",i.getStringExtra("id"));
                toTicket.putExtra("details",i.getStringExtra("details"));
                toTicket.putExtra("img",i.getStringExtra("img"));
                startActivity(toTicket);
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