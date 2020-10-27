package com.example.showtime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class FaqAdapter extends FirestoreRecyclerAdapter<FaqModel, FaqAdapter.FaqViewHolder> {

    private MovieAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{

        void clickOnItem(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(MovieAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FaqAdapter(@NonNull FirestoreRecyclerOptions<FaqModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FaqAdapter.FaqViewHolder faqViewHolder, int i, @NonNull FaqModel faqModel) {

        faqViewHolder.question.setText("Question:\n"+faqModel.getQuestion());
        faqViewHolder.answer.setText("Answer:\n"+faqModel.getAnswer());
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public FaqAdapter.FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_list, parent, false);
        return new FaqAdapter.FaqViewHolder(view);
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder {

        private TextView question;
        private TextView answer;

        public FaqViewHolder(@NonNull View itemView) {

            super(itemView);
            question = itemView.findViewById(R.id.question_list);
            answer = itemView.findViewById(R.id.answer_list);
        }

    }
}
