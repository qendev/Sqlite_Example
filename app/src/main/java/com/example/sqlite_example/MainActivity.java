package com.example.sqlite_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //create member variables
    private GroceryAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private EditText editText_name;
    private TextView textView_amount;
    private int mAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        //to add items to the database
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryAdapter(this,getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;

            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(recyclerView);
        //assign the member variables
        editText_name = findViewById(R.id.editText_name);
        textView_amount = findViewById(R.id.textView_amount);

        //create variable for the buttons
        Button button_increase = findViewById(R.id.button_increase);
        Button button_decrease = findViewById(R.id.button_decrease);
        Button button_add = findViewById(R.id.button_add);

        //set onClicklisteners to those buttons
        button_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we increase the amount
                increase();
            }
        });

        button_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hre we decrease the amount
                decrease();
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we add new item
                addItem();
            }
        });
    }
    private void increase(){
        mAmount++;
        textView_amount.setText(String.valueOf(mAmount));
    }

    private void decrease(){
        if(mAmount>0){
            mAmount--;
            textView_amount.setText(String.valueOf(mAmount));
        }

    }

    private void addItem(){
        if(editText_name.getText().toString().trim().length()==0 || mAmount ==0){
            return;
        }
        String name = editText_name.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(GroceryContract.GroceryEntry.COLUMN_NAME,name);
        cv.put(GroceryContract.GroceryEntry.COLUMN_AMOUNT,mAmount);

        mDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME,null,cv);
        mAdapter.swapCursor(getAllItems());
        editText_name.getText().clear();
    }

    private void removeItem(long id){
        mDatabase.delete(GroceryContract.GroceryEntry.TABLE_NAME,
                GroceryContract.GroceryEntry._ID + "=" + id,null);
        mAdapter.swapCursor(getAllItems());
    }
    //returns a cursor
    private Cursor getAllItems(){
        return mDatabase.query(
                GroceryContract.GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryContract.GroceryEntry.COLUMN_TIMESTAMP + " DESC"
        );

    }
}