package com.example.journal2;

import android.content.Context;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import java.text.ParseException;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.Date;

public class EntryAdapter extends ResourceCursorAdapter {

    public EntryAdapter(Context context, Cursor c) {
        super(context, R.layout.entry_row, c, true);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // get/set texts
        TextView title = view.findViewById(R.id.titleText);
        int titleIndex = cursor.getColumnIndex("title");
        title.setText(cursor.getString(titleIndex));

        //get/set date
        TextView date = view.findViewById(R.id.dateText);
        int dateIndex = cursor.getColumnIndex("timestamp");
        String dateStr = cursor.getString(dateIndex);
        try {
            Date dateObj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
            date.setText(DateUtils.getRelativeDateTimeString(context, dateObj.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // get/set content
        TextView content = view.findViewById(R.id.contentText);
        int contentIndex = cursor.getColumnIndex("content");
        content.setText(cursor.getString(contentIndex));

    }
}