package com.example.coordinator;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private DocumentReference docRef;
    private Spinner spinner;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initFirestore();
        getSupportActionBar().setTitle("Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        tag = getResources().getStringArray(R.array.sport_types)[0];
        initSpinner();
        FloatingActionButton button_post = findViewById(R.id.button_send_post);
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
        if (!tag.equals("Choose category")) {
            EditText editText = findViewById(R.id.editText);
            String text = editText.getText().toString();
            if (text.length() > 0) {
                CollectionReference posts = firestore.collection("Posts");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault());
                String date = sdf.format(new Date());
                posts.add(new Post(text, tag, date, new ArrayList<Comment>()));
                makeToastMessage("Send");
                finish();
            } else makeToastMessage("Textfield is empty");
        }
        else {
            makeToastMessage("Choose category");
            spinner.performClick();
        }
    }

    private void makeToastMessage(CharSequence show) {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, show, duration);
        toast.show();
    }
}
