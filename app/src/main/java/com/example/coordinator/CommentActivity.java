package com.example.coordinator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommentActivity extends AppCompatActivity {
    private CommentAdapterF adapter;
    private CollectionReference colRef;
    private FirebaseFirestore firestore;
    private DocumentReference docRef;
    private Query query;
    private final int LIMIT = 50;
    private EditText editText_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(R.string.comments);
        initFirestore();
        setUpPostAdapter();
        editText_comment = findViewById(R.id.editText_comment);
        FloatingActionButton button = findViewById(R.id.button_send_comment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
    }

    private void initFirestore() {
        firestore = FirebaseFirestore.getInstance();
        String document_id = getIntent().getStringExtra("document_id");
        docRef = firestore.collection("Posts").document(document_id);
        colRef = docRef.collection("comments");
        query = colRef
                .orderBy("date", Query.Direction.ASCENDING)
                .limit(LIMIT);
    }

    private void setUpPostAdapter() {
        FirestoreRecyclerOptions<Comment> options = new FirestoreRecyclerOptions.Builder<Comment>()
                .setQuery(query, Comment.class).build();
        adapter = new CommentAdapterF(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void sendComment() {
        String text = editText_comment.getText().toString();
        if (text.length() > 0) {
            makeToastMessage("Commented");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault());
            String date = sdf.format(new Date());
            Comment comment = new Comment(text, date);
            editText_comment.setText("");
            colRef.add(comment);
            docRef.update("commentCount", FieldValue.increment(1));
        }
        else makeToastMessage("Write a comment");
    }

    private void makeToastMessage(CharSequence show) {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, show, duration);
        toast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
