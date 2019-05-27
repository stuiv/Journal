package com.example.journal2;

import java.io.Serializable;
import java.sql.Timestamp;

public class JournalEntry implements Serializable {

    private String title, content;
    private Timestamp timestamp;
    private EntryDatabase.MoodType mood;

    public JournalEntry(long id, EntryDatabase.MoodType mood, String title, String content,
                            Timestamp timestamp) {
        this.title = title;
        this.content = content;
        this.mood = mood;
        this.timestamp = timestamp;
    }

    String getTitle() {
        return title;
    }

    String getContent() {
        return content;
    }

    Timestamp getTimestamp() {
        return timestamp;
    }

    EntryDatabase.MoodType getMood() {
        return mood;
    }
}
