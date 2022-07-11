package com.example.mylibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.ViewHolder> {
    //ctrl+i
    private static final String TAG = "BooksRecViewAdapter";
    private ArrayList<Book> books = new ArrayList<Book>();
    private Context mContext;
    private ViewHolder holder;
    private int position;

    public BooksRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_books,parent,false);
        //ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@NonNull  int position){
      Log.d(TAG,"onBindViewHolder: Called");
      holder.txtName.setText(books.get(position).getName());
      Glide.with(mContext)
              .asBitmap()
              .load(books.get(position).getImageUrl())
              .into(holder.imgBook);
      holder.parent.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(mContext, "selected", Toast.LENGTH_SHORT).show(); //books.get(position).getName()
          }
      });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgBook;
        private TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
