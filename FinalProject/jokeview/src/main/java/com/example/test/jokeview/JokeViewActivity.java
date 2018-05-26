package com.example.test.jokeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_view);

        String joke = getIntent().getStringExtra("JOKE_KEY");
        TextView jokeTv = findViewById(R.id.joke_tv);
        jokeTv.setText(joke);
    }
}
