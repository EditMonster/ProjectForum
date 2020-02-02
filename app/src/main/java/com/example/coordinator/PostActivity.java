package com.example.coordinator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private Spinner spinner;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initFirestore();
        tag = getResources().getStringArray(R.array.sport_types)[0];
        initSpinner();
        Button button_post = findViewById(R.id.button_confirm_post);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePost();
            }
        });
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sport_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFirestore() {
        firestore = FirebaseFirestore.getInstance();
    }

    private void makePost() {
        EditText editText = findViewById(R.id.editText);
        String text = editText.getText().toString();
        CollectionReference posts = firestore.collection("Posts");
        posts.add(new Post(text, tag));
    }
}
