package com.example.ziyi.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String newID;

    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.show);
        Button button_add = (Button)findViewById(R.id.add);
        Button button_delete = (Button)findViewById(R.id.delete);
        Button button_update = (Button)findViewById(R.id.update);
        Button button_search = (Button)findViewById(R.id.search);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.ziyi.sqlite.database.provider/" + Words.Word.TABLE_NAME);
                ContentValues contentValues = new ContentValues();
                contentValues.put(Words.Word.COLUMN_NAME_WORD,"star");
                contentValues.put(Words.Word.COLUMN_NAME_MEANING,"star");
                contentValues.put(Words.Word.COLUMN_NAME_SAMPLE,"Star is beautiful!");

                Uri newUri = getContentResolver().insert(uri,contentValues);
                newID = newUri.getPathSegments().get(1);
            }
        });

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.ziyi.sqlite.database.provider/" + Words.Word.TABLE_NAME);
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                String results = "";

                if(cursor != null){
                    while (cursor.moveToNext()){
                        String word = cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD));
                        String meaning = cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING));
                        String sample = cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE));

                        results = results + word + "\n" + meaning + "\n" + sample + "\n";
                    }
                    cursor.close();
                }

                textView.setText(results);
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.ziyi.sqlite.database.provider/" + Words.Word.TABLE_NAME);
                ContentValues contentValues = new ContentValues();
                contentValues.put(Words.Word.COLUMN_NAME_WORD,"flower");
                contentValues.put(Words.Word.COLUMN_NAME_MEANING,"flower");
                contentValues.put(Words.Word.COLUMN_NAME_SAMPLE,"Flower is red.");
                getContentResolver().update(uri,contentValues,Words.Word.COLUMN_NAME_WORD + "=?",new String[] {"star"});
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.ziyi.sqlite.database.provider/" + Words.Word.TABLE_NAME );
                Log.v("tag","newID "+newID);
                getContentResolver().delete(uri, Words.Word.COLUMN_NAME_WORD + "=?",new String[] {"flower"});
            }
        });
    }
}
