package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminChatList extends AppCompatActivity {

    RecyclerView recyclerView;
    ChatAdminAdapter adapter;
    ArrayList<String> list = new ArrayList<>();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat_list);
        recyclerView = findViewById(R.id.recyclerViewChatList);

        constructRecyclerView();
    }

    private void constructRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        firebaseFirestore.collection("chat").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    adapter = new ChatAdminAdapter(list);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener(new ChatAdminAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            Log.d("click",list.get(position)+"");
                            Intent toLiveChatAdmin = new Intent(getApplicationContext(),LiveChatAdmin.class);
                            toLiveChatAdmin.putExtra("id",list.get(position));
                            startActivity(toLiveChatAdmin);
                        }
                    });
                    Log.d("chat list", list.toString());
                }else {
                    Log.d("fail chat list", "Error getting documents: ", task.getException());
                }
            }
        });

    }

}