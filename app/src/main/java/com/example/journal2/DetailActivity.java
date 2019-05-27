package com.example.journal2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.journal2.EntryDatabase.*;


public class DetailActivity extends AppCompatActivity {

    private JournalEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Your journal entry");

        // get the intent, which stored an entry object
        Intent intent = getIntent();
        entry = (JournalEntry) intent.getSerializableExtra("entry");

        // load views
        TextView title = findViewById(R.id.titleText);
        TextView content = findViewById(R.id.contentText);
        TextView date = findViewById(R.id.dateText);
        TextView moodname =  findViewById(R.id.moodname);

        // set info for journal activity.
        title.setText(entry.getTitle());
        content.setText(entry.getContent());
        date.setText(entry.getTimestamp().toString());
        moodname.setText(entry.getMood().toString());
        ImageView moodImg = findViewById(R.id.moodImg);

        MoodType mood = entry.getMood();
        moodImg.setImageResource(mood.getResourceId());
    }
}

