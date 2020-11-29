package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class DeleteMovies extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    MovieAdapter adapter;
    StorageReference ref= FirebaseStorage.getInstance().getReference();
    AlertDialog.Builder display_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_movies);
        recyclerView = findViewById(R.id.recyclerView_List);

        constructRecyclerView();

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
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                display_alert = new AlertDialog.Builder(DeleteMovies.this);
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
                //adapter.deleteItem(viewHolder.getAdapterPosition());
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
}