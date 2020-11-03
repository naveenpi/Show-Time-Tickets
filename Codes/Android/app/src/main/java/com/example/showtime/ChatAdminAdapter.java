package com.example.showtime;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class ChatAdminAdapter extends RecyclerView.Adapter<ChatAdminAdapter.ListViewHolder> {

    private static ClickListener clickListener;
    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        ChatAdminAdapter.clickListener = clickListener;
    }
    private final ArrayList<String> list;

    public ChatAdminAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChatAdminAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list, parent, false);
        return new ChatAdminAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdminAdapter.ListViewHolder holder, int position) {

        String uid = list.get(position);
        Log.d("adapter uid",uid);
        holder.UID.setText("User: "+uid);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView UID;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            UID = itemView.findViewById(R.id.chatMemberName);
        }

        @Override
        public void onClick(View v) {
            ChatAdminAdapter.clickListener.onItemClick(getAdapterPosition(),v);
        }
    }
}