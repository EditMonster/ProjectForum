package com.example.coordinator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class PostAdapter extends FirestoreRecyclerAdapter<Post, PostAdapter.PostHolder> {
    private OnCardClickListener onCardClickListener;
    private Context fContext;
    private int[] colors;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PostAdapter(@NonNull FirestoreRecyclerOptions<Post> options, OnCardClickListener onCardClickListener, Context fContext) {
        super(options);
        this.onCardClickListener = onCardClickListener;
        this.fContext = fContext;
        getColors();
    }

    @Override
    protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull Post model) {
        holder.tag.setText(model.getTag());
        holder.text.setText(model.getText());
        String date = model.getDate();
        if (date != null) {
            String time = date.substring(9, 11) + '.' + date.substring(11, 13);
            holder.time.setText(time);
            String count = "" + model.getCommentCount();
            holder.commentCoumt.setText(count);
        }
        if (model.getColor() >= 0 && model.getColor() < 4) {
            holder.postLayout.setBackgroundColor(colors[model.getColor()]);
        }
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new PostHolder(view, onCardClickListener);
    }
    public class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout postLayout;
        TextView tag;
        TextView text;
        TextView time;
        TextView commentCoumt;
        OnCardClickListener onCardClickListener;


        public PostHolder(@NonNull View itemView, OnCardClickListener onCardClickListener) {
            super(itemView);
            postLayout = itemView.findViewById(R.id.constraint_post);
            tag = itemView.findViewById(R.id.text_view_tag);
            text = itemView.findViewById(R.id.text_view_text);
            time = itemView.findViewById(R.id.text_view_time);
            commentCoumt = itemView.findViewById(R.id.text_view_commentCount);
            this.onCardClickListener = onCardClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardClickListener.onCardClick(getSnapshots().getSnapshot(getAdapterPosition()));
        }
    }

    public interface OnCardClickListener {
        void onCardClick(DocumentSnapshot documentSnapshot);
    }

    private void getColors() {
        colors = new int[4];
        colors[0] = ContextCompat.getColor(fContext, R.color.colorPost1);
        colors[1] = ContextCompat.getColor(fContext, R.color.colorPost2);
        colors[2] = ContextCompat.getColor(fContext, R.color.colorPost3);
        colors[3] = ContextCompat.getColor(fContext, R.color.colorPost4);
    }
}
