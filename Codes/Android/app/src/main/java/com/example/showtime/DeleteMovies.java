package com.example.showtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class DeleteMovies extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirestoreRecyclerAdapter adapter;
    StorageReference ref= FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_movies);
        recyclerView = findViewById(R.id.recyclerView_List);

        Query query = firebaseFirestore.collection("movie");

        FirestoreRecyclerOptions<MoviesModel> response = new FirestoreRecyclerOptions.Builder<MoviesModel>()
                .setQuery(query, MoviesModel.class)
                .build();
        Log.d("query","This is query: "+query.toString());
        //Log.println(1,"query")
        adapter = new FirestoreRecyclerAdapter<MoviesModel, MovieViewHolder>(response) {
            @NonNull
            @Override
            public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new MovieViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MovieViewHolder holder, int position,  MoviesModel model) {

//                model = new MoviesModel();
                //holder.movieImage.setImageURI(Uri.parse(model.getImgReference()));
                //Log.d("image path","image path: "+ref.child(model.getImgReference()).getDownloadUrl());

//                final String[] uriString = new String[1];
//                ref.child(model.getImgReference()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//                        uriString[0] =uri.toString();
//
//                    }
//                });
                Log.d("image path","image path: "+model.getImgReference());
                Glide.with(getApplicationContext())
                        .load(model.getImgReference())
                        .override(70,194)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(holder.movieImage);
                holder.movieName.setText(model.getMovieName());
                Log.d("from database",model.getMovieName());
                holder.genre.setText(model.getGenre());
                holder.rating.setText(model.getRating());
                holder.price.setText(model.getTicketPrice());


            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieImage;
        private TextView movieName;
        private TextView genre;
        private TextView rating;
        private TextView price;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage=itemView.findViewById(R.id.image_List);
            movieName = itemView.findViewById(R.id.MovieName_List);
            genre = itemView.findViewById(R.id.Genre_List);
            rating=itemView.findViewById(R.id.Rating_List);
            price=itemView.findViewById(R.id.TicketPrice_List);
        }
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