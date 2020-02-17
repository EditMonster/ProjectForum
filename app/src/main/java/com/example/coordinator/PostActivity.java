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
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PostActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private Spinner spinner;
    TextInputEditText editText;
    private int[] colors;
    private int color;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initFirestore();
        getSupportActionBar().setTitle(R.string.new_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        tag = getResources().getStringArray(R.array.sport_types)[0];
        initSpinner();
        setColors();
        FloatingActionButton button_post = findViewById(R.id.button_send_post);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePost();
            }
        });
        FloatingActionButton button_color = findViewById(R.id.button_change_color);
        button_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
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

    private void changeColor() {
        if (color < 3) color++;
        else color = 0;
        editText.setBackgroundColor(colors[color]);
    }

    private void randomizeColor() {
        final int random = new Random().nextInt(4);
        color = random;
        editText.setBackgroundColor(colors[random]);
    }

    private void getColors() {
        colors = new int[4];
        colors[0] = ContextCompat.getColor(this, R.color.colorPost1);
        colors[1] = ContextCompat.getColor(this, R.color.colorPost2);
        colors[2] = ContextCompat.getColor(this, R.color.colorPost3);
        colors[3] = ContextCompat.getColor(this, R.color.colorPost4);
    }

    private void setColors() {
        editText = findViewById(R.id.editText_post);
        getColors();
        randomizeColor();
    }

    private void initFirestore() {
        firestore = FirebaseFirestore.getInstance();
    }

    private void makePost() {
        if (!tag.equals("Choose category")) {
            EditText editText = findViewById(R.id.editText_post);
            String text = editText.getText().toString();
            if (text.length() > 0) {
                CollectionReference posts = firestore.collection("posts17_2");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault());
                String date = sdf.format(new Date());
                Post post = new Post(text, tag, date, color);
                posts.add(post);
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
