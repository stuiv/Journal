package com.example.journal2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        setTitle("New entry");
    }

    public void Journal_entry(View view) {

        RadioGroup Moods_radio = findViewById(R.id.moodGroup);

        int selectedRadioId = Moods_radio.getCheckedRadioButtonId();
        EntryDatabase.MoodType mood;

        switch (selectedRadioId) { // Moodtype options
            case R.id.radioHappy:
                mood = EntryDatabase.MoodType.ANGRY;
                break;
            case R.id.radioEmbarrassed:
                mood = EntryDatabase.MoodType.EMBARRASSED;
                break;
            case R.id.radioProud:
                mood = EntryDatabase.MoodType.PROUD;
                break;
            case R.id.radioSad:
                mood = EntryDatabase.MoodType.SAD;
                break;
            case R.id.radioScared:
                mood = EntryDatabase.MoodType.SCARED;
                break;
            case R.id.radioSuperHappy:
                mood = EntryDatabase.MoodType.SUPER_HAPPY;
                break;
            case R.id.radioUpset:
                mood = EntryDatabase.MoodType.UPSET;
                break;
            case R.id.radioWorried:
                mood = EntryDatabase.MoodType.WORRIED;
                break;

            // basecase
            default:
                mood = EntryDatabase.MoodType.HAPPY;
        }

        TextView Title = findViewById(R.id.edit_titleEdit);
        TextView content = findViewById(R.id.contentEditText);

        String titleString = Title.getText().toString();
        String contentString = content.getText().toString();

        // Give warning if not all fields are filled.
        if (titleString.length() == 0 || contentString.length() == 0) {
            String String_error = "Whoops! you didn't fill every field!";
            Toast toast = Toast.makeText(this, String_error , Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // Insert Journal Entry
        EntryDatabase.getInstance(this).insertEntry(
                new JournalEntry(0, mood, titleString, contentString, null));
        startActivity(new Intent(this, MainActivity.class));
    }

}
