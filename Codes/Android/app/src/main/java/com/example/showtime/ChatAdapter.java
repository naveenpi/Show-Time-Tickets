package com.example.showtime;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private final ArrayList<ChatMessage> list;

//    public ChatAdapter(@NonNull FirestoreRecyclerOptions<ChatMessage> options) {
//        super(options);
//    }

    public ChatAdapter(ArrayList<ChatMessage> objects) {
        list = objects;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder chatViewHolder, int i) {


        ChatMessage msg = new ChatMessage((Map) list.get(i));
        chatViewHolder.messageText.setText("message: "+msg.getMessageText());
        chatViewHolder.messageUser.setText("User: "+msg.getMessageUser());
        chatViewHolder.messageTime.setText("Time: "+ DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                msg.getMessageTime()));
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        return new ChatAdapter.ChatViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder{

        public TextView messageText;
        public TextView messageUser;
        public TextView messageTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
            messageUser = itemView.findViewById(R.id.message_user);
            messageTime = itemView.findViewById(R.id.message_time);
        }

    }
}


