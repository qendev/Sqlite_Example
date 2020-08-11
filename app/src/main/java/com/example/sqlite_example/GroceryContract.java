package com.example.sqlite_example;

import android.provider.BaseColumns;

public class GroceryContract {

    public GroceryContract() {}

    public static final class GroceryEntry implements BaseColumns{
        //create string constants
        //name of the table
        public static final String TABLE_NAME = "groceryList";

        //name of the first column of the table
        public static final String COLUMN_NAME = "name";

        //name of the column we save the amount of an item
        public static final String COLUMN_AMOUNT = "amount";

        //name of timestamp for ordering items in the list
        public static final String COLUMN_TIMESTAMP ="timestamp";
    }
}
