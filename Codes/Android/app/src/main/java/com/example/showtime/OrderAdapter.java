package com.example.showtime;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class OrderAdapter  extends FirestoreRecyclerAdapter<OrderModel, OrderAdapter.OrderViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    String moviePoster="";
    private OrderAdapter.OnItemClickListener listener;
    public OrderAdapter(@NonNull FirestoreRecyclerOptions<OrderModel> options) {
        super(options);
    }

    public interface OnItemClickListener{

        void clickOnItem(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OrderAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull OrderModel orderModel) {


        orderViewHolder.bookingRef.setText("Proceed to Payment by clicking");
        orderViewHolder.movieName.setText("Movie Name: "+orderModel.getMovieName());
        orderViewHolder.dateTimeSeats.setText("Date: "+orderModel.getDate()+"\nTime: "+orderModel.getTime()+"\nSeats: "+orderModel.getSeats());
        orderViewHolder.moviePoster.setImageResource(R.drawable.ic_movie_black_24dp);
        Log.d("poster", moviePoster);
        //Picasso.get().load(moviePoster).into(orderViewHolder.moviePoster);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private ImageView moviePoster;
        private TextView bookingRef;
        private TextView movieName;
        private TextView dateTimeSeats;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.MoviePosterOrderList);
            bookingRef = itemView.findViewById(R.id.bookingRefOrderList);
            movieName = itemView.findViewById(R.id.MovieNameOrderList);
            dateTimeSeats = itemView.findViewById(R.id.DateTimeSeatsOrderList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.clickOnItem(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
}
