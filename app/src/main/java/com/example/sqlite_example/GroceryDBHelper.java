package com.example.sqlite_example;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sqlite_example.GroceryContract.*;

import androidx.annotation.Nullable;

public class GroceryDBHelper extends SQLiteOpenHelper {
    //create two constants
    //name of the database
    public static final String DATABASEA_NAME = "grocerylist.db";

    //name of the version
    public static final int DATABASE_VERSION = 1;


    public GroceryDBHelper(Context context) {
        super(context, DATABASEA_NAME, null, DATABASE_VERSION);
    }

    @Override
    //called we we first create the database
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +
               GroceryEntry.TABLE_NAME + " (" +
                GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GroceryEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                GroceryEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                GroceryEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);

    }

    @Override
    //called when you want to increment the version number manualy;if you want to change the schema of the database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.TABLE_NAME);
        onCreate(db);

    }
}
