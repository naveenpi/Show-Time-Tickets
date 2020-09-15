package com.example.showtime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PaymentsAdapter extends FirestoreRecyclerAdapter<PaymentsModel, PaymentsAdapter.PaymentViewHolder> {




    public PaymentsAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PaymentViewHolder paymentViewHolder, int i, @NonNull PaymentsModel paymentsModel) {

        paymentViewHolder.cardNumberList.setText(paymentsModel.getCardNumber());
        paymentViewHolder.nameOnCardList.setText(paymentsModel.getNameOnCard());
        paymentViewHolder.expirationList.setText("Expiration: "+paymentsModel.getMonth()+"/"+paymentsModel.getYear());
        paymentViewHolder.cardHolder.setImageResource(R.drawable.ic_baseline_credit_card_24);
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payments_list, parent, false);
        return new PaymentsAdapter.PaymentViewHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        private ImageView cardHolder;
        private TextView cardNumberList;
        private TextView nameOnCardList;
        private TextView expirationList;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNumberList = itemView.findViewById(R.id.CardNumberList);
            nameOnCardList = itemView.findViewById(R.id.NameOnCardList);
            expirationList = itemView.findViewById(R.id.ExpirationList);
            cardHolder = itemView.findViewById(R.id.cardHolder);
        }
    }
}
