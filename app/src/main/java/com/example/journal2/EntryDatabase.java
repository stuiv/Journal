package com.example.journal2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.HashMap;
import java.util.Map;

public class EntryDatabase extends android.database.sqlite.SQLiteOpenHelper {

    protected static EntryDatabase instance;

    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int v) {
        super(context, name, factory, v);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
    // Create database
        String query;
        query = "CREATE TABLE entries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "content TEXT," +
                "mood INT  ,zerofill NOT NULL," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";

        database.execSQL(query);
    }

    @Override //update database
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS entries");
        onCreate(database);
    }

    static EntryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new EntryDatabase(context, "entries", null, 3);
        }
        return instance;
    }

    Cursor selectAll() {
        return getWritableDatabase().rawQuery("SELECT * FROM entries ORDER BY timestamp DESC", null);
    }

    void insertEntry(JournalEntry entry) {
        ContentValues Journal_entry = new ContentValues();
        Journal_entry.put("Title", entry.getTitle());
        Journal_entry.put("Content", entry.getContent());
        Journal_entry.put("Mood", entry.getMood().getValue());

        SQLiteDatabase database = getWritableDatabase();
        database.insert("entries", null, Journal_entry);
        database.close();
    }

    void deleteEntry(Long id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("entries", "_id = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public enum MoodType {
        /**
         * Moodtypes
         */
        ANGRY(1), EMBARRASSED(2), HAPPY(3), PROUD(4), SAD(5),
        SCARED(6), SUPER_HAPPY(7), UPSET(8), WORRIED(9);

        private int value;
        private int resourceId;
        private static Map Mood_map = new HashMap<>();

        MoodType(int value) {

            this.value = value;

            switch (this.value) {
                case 1:
                    resourceId = R.drawable.angry;
                    break;
                case 2:
                    resourceId = R.drawable.embarressed;
                    break;
                case 3:
                    resourceId = R.drawable.happy;
                    break;
                case 4:
                    resourceId = R.drawable.proud;
                    break;
                case 5:
                    resourceId = R.drawable.sad;
                    break;
                case 6:
                    resourceId = R.drawable.scared;
                    break;
                case 7:
                    resourceId = R.drawable.super_happy;
                    break;
                case 8:
                    resourceId = R.drawable.upset;
                    break;
                default:
                    resourceId = R.drawable.worried;
            }
        }

        static {
            for (MoodType moodType : MoodType.values()) Mood_map.put(moodType.value, moodType);
        }

        public static MoodType valueOf(int moodType) {
            return (MoodType) Mood_map.get(moodType);
        }

        private int getValue() {
            return value;
        }

        public int getResourceId() {
            return resourceId;
        }
    }
}