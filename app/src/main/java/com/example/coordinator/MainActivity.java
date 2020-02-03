package com.example.coordinator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FloatingActionButton button_post;
    private Query query;
    PostAdapter adapter;
    private RecyclerView recyclerView;
    final int LIMIT = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Log.d("jee", "jaa");
            signInAnonymously();
        }
        initFirestore();
        setUpPostAdapter();
        button_post = findViewById(R.id.button_floating_post);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPost();
            }
        });
    }

    private void initFirestore() {

        firestore = FirebaseFirestore.getInstance();
        query = firestore.collection("Posts")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(LIMIT);
    }

    private void startPost() {
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, PostActivity.class);
            startActivity(intent);
        }
        else {
            DialogNotSigned dialogNotSigned = new DialogNotSigned();
            dialogNotSigned.show(getSupportFragmentManager(), "Not signed");
        }
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("success", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("fail", "signInAnonymously:failure", task.getException());
                        }
                    }
                });
    }

    private void setUpPostAdapter() {
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class).build();
        adapter = new PostAdapter(options);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
