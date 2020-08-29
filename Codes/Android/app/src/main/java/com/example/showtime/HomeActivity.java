package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener
{

    TextView b1,b2,b3,b4;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    MovieAdapter adapter;
    StorageReference ref= FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView=findViewById(R.id.recycler_home);
        b1=findViewById(R.id.button_home);
        b2=findViewById(R.id.button2_home);
        b3=findViewById(R.id.button3_home);
        b4=findViewById(R.id.button4_home);

        constructRecyclerView();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void constructRecyclerView() {

        Query query = firebaseFirestore.collection("movie");
        FirestoreRecyclerOptions<MoviesModel> response = new FirestoreRecyclerOptions.Builder<MoviesModel>()
                .setQuery(query, MoviesModel.class)
                .build();
        Log.d("query","This is query: "+query.toString());

        adapter = new MovieAdapter(response);
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
    
    public void menu(View v) {
        PopupMenu popup= new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.navigation_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                return true;
            case R.id.item2:
                Intent toLogin=new Intent(this,MainActivity.class);
                startActivity(toLogin);
                return true;
            case R.id.item3:
                Intent toForgotPassword=new Intent(this,ForgotPassword.class);
                startActivity(toForgotPassword);
                return true;
            default:
                Intent toSignUP=new Intent(this,SignUp.class);
                startActivity(toSignUP);
                return true;
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
