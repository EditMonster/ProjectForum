package com.example.coordinator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentActivity extends AppCompatActivity {
    private CommentAdapter adapter;
    private DocumentReference docRef;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private Post post;
    EditText editText_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comments");
        initFirestore();
        loadPost();
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
    }

    private void loadPost() {
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                post = documentSnapshot.toObject(Post.class);
                setUpPostAdapter();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void setUpPostAdapter() {
        adapter = new CommentAdapter(this, post.getComments());
        recyclerView = findViewById(R.id.recycler_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void sendComment() {
        String text = editText_comment.getText().toString();
        if (text.length() > 0) {
            makeToastMessage("Commented");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault());
            String date = sdf.format(new Date());
            Comment comment = new Comment(text, date);
            post.addComment(comment);
            editText_comment.setText("");
            docRef.update("comments", post.getComments());
        }
        else makeToastMessage("Write a comment");
    }

    private void makeToastMessage(CharSequence show) {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, show, duration);
        toast.show();
    }
}
