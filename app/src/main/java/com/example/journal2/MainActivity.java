package com.example.journal2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity {

    private EntryDatabase database;
    private EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Journal:");

        ListView entriesList = findViewById(R.id.entries_List);
        database = EntryDatabase.getInstance(getApplicationContext());

        Cursor all_entries = database.selectAll();
        adapter = new EntryAdapter(this, all_entries);
        entriesList.setAdapter(adapter);

        // set listeners
        entriesList.setOnItemClickListener(new OnJournalItemClicked());
        entriesList.setOnItemLongClickListener(new OnJournalItemLongClicked());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update data
        adapter.swapCursor(database.selectAll());
    }

    public void onAddClicked(View view) {
        startActivity(new Intent(this, InputActivity.class));
    }

//  Go to Item
    private class OnJournalItemClicked implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            int titleIndex = cursor.getColumnIndex("title");
            int contentIndex = cursor.getColumnIndex("content");
            int moodIndex = cursor.getColumnIndex("mood");
            int timeIndex = cursor.getColumnIndex("timestamp");
            int mood = cursor.getInt(moodIndex);

            // Proces journal entry
            JournalEntry journal = new JournalEntry(id, EntryDatabase.MoodType.valueOf(mood),
                    cursor.getString(titleIndex), cursor.getString(contentIndex),
                    Timestamp.valueOf(cursor.getString(timeIndex)));

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("entry", journal);
            startActivity(intent);
        }
    }

//  Delete item
    private class OnJournalItemLongClicked implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            database.deleteEntry(id);

            // Update Data
            adapter.swapCursor(database.selectAll());
            return true;
        }
    }
}
