package com.example.showtime;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends FirestoreRecyclerAdapter<MoviesModel, MovieAdapter.MovieViewHolder> {


    private OnItemClickListener listener;
    public MovieAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    public interface OnItemClickListener{

        void clickOnItem(DocumentSnapshot documentSnapshot, int position);
        void clickOnButton(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position, @NonNull MoviesModel model) {

        Picasso.get().load(model.getImgReference()).into(holder.movieImage);
//        holder.movieImage.setImageResource(R.drawable.bahubali);
        Log.d("image path","image path: "+model.getImgReference());
        holder.movieName.setText("Movie: "+model.getMovieName());
        holder.genre.setText("Genre: "+model.getGenre());
        holder.rating.setText("Rating: "+model.getRating());
        holder.price.setText("Price: $"+model.getTicketPrice());
        holder.theater.setText("Theater: "+model.getTheater());
        holder.location.setText("Location: "+model.getLocation());
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieImage;
        private TextView movieName;
        private TextView genre;
        private TextView rating;
        private TextView price;
        private TextView theater;
        private TextView location;
        private Button feedback;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage=itemView.findViewById(R.id.image_List);
            movieName = itemView.findViewById(R.id.MovieName_List);
            genre = itemView.findViewById(R.id.Genre_List);
            rating=itemView.findViewById(R.id.Rating_List);
            price=itemView.findViewById(R.id.TicketPrice_List);
            theater = itemView.findViewById(R.id.Theater_list);
            location = itemView.findViewById(R.id.Location_list);
            feedback = itemView.findViewById(R.id.HomeFeedback);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.clickOnItem(getSnapshots().getSnapshot(position),position);
                    }
                }
            });

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.clickOnButton(v);
                    }
                }
            });

        }
    }


}
