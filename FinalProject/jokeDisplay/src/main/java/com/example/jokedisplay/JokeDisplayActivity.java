package com.example.jokedisplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(JOKE_EXTRA)) {
            String jokeRetrieved = intent.getStringExtra(JOKE_EXTRA);
            TextView textView = findViewById(R.id.textView_joke);
            textView.setText(jokeRetrieved);
        }
    }
}
