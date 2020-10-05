package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
    EditText search;
    Boolean i=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView=findViewById(R.id.recycler_home);
        b1=findViewById(R.id.button_home);
        b2=findViewById(R.id.button2_home);
        b3=findViewById(R.id.button3_home);
        b4=findViewById(R.id.button4_home);
        search=findViewById(R.id.search_Box);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        constructRecyclerView("");
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

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d("search",search.getText().toString());
                constructRecyclerView(search.getText().toString());
                adapter.startListening();
            }
        });


    }



    private void constructRecyclerView(String s) {

        Query query = firebaseFirestore.collection("movie").orderBy("movieName").startAt(s).endAt(s+"\uf8ff");

        if(!testQuery(s))
            query = firebaseFirestore.collection("movie").orderBy("genre").startAt(s).endAt(s + "\uf8ff");

        FirestoreRecyclerOptions<MoviesModel> response = new FirestoreRecyclerOptions.Builder<MoviesModel>()
                .setQuery(query, MoviesModel.class)
                .build();
        //Log.d("query","This is query: "+query.get().getResult().toString());
        Log.d("response","response: "+response.toString());

        adapter = new MovieAdapter(response);
        //recyclerView.setHasFixedSize(true);

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

        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void clickOnItem(DocumentSnapshot documentSnapshot, int position) {

                Intent i = new Intent(getApplicationContext(),TimeActivity.class);
                Log.d("intent ",documentSnapshot.get("movieName").toString());
                i.putExtra("movieName",documentSnapshot.get("movieName").toString());
                startActivity(i);

            }
        });
    }

    private boolean testQuery(String s) {

        firebaseFirestore.collection("movie").orderBy("movieName").startAt(s).endAt(s+"\uf8ff").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("query success", document.getId() + " => " + document.getData());
                        i=true;
                    }
                } else {
                    Log.d("query fail", "Error getting documents: ", task.getException());
                    i=false;
                }
            }
        });
        return i;
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        Log.d("stop","stop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        Log.d("start","start");
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

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_sms:
                            openFragment(SmsFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_notifications:
                            openFragment(NotificationFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };
}