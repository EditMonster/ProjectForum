package com.example.coordinator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PostAdapter extends FirestoreRecyclerAdapter<Post, PostAdapter.PostHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PostAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull Post model) {
        holder.tag.setText(model.getTag());
        holder.text.setText(model.getText());
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new PostHolder(view);
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        TextView tag;
        TextView text;


        public PostHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.text_view_tag);
            text = itemView.findViewById(R.id.text_view_text);
        }
    }
}
