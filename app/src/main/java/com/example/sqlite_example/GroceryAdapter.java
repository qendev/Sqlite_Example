package com.example.sqlite_example;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public GroceryAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor =cursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        public TextView  textView_name_item;
        public TextView textView_amount_item;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_name_item = itemView.findViewById(R.id.textView_name_item);
            textView_amount_item = itemView.findViewById(R.id.textView_amount_item);
        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grocery_item,parent,false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        //to make sure data is dispalyed on the item
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_AMOUNT));

        //read id out of the database
        long id = mCursor.getLong(mCursor.getColumnIndex(GroceryContract.GroceryEntry._ID));


        holder.textView_name_item.setText(name);
        holder.textView_amount_item.setText(String.valueOf(amount));
        holder.itemView.setTag(id);



    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor!=null){
            mCursor.close();
            mCursor = newCursor;

            if(newCursor!=null){
                notifyDataSetChanged();
            }
        }
    }
}
