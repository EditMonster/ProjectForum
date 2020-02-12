package com.example.coordinator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CommentAdapterF extends FirestoreRecyclerAdapter<Comment, CommentAdapterF.CommentHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CommentAdapterF(@NonNull FirestoreRecyclerOptions<Comment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CommentHolder holder, int position, @NonNull Comment model) {
        holder.text.setText(model.getText());
        String date = model.getDate();
        if (date != null) {
            String time = model.getDate().substring(9, 11) + '.' + model.getDate().substring(11, 13);
            holder.time.setText(time);
        }
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comment, parent, false);
        return new CommentHolder(view);
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView time;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text_view_comment);
            time = itemView.findViewById(R.id.text_view_comment_time);
        }
    }
}
