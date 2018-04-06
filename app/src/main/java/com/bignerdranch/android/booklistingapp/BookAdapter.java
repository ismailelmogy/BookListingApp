package com.bignerdranch.android.booklistingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ismail on 05/03/18.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> androidBooks){

        super(context, 0, androidBooks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);


        TextView bookTitle = listItemView.findViewById(R.id.book_title);
        bookTitle.setText(currentBook.getBookTitle());

        TextView bookAuthor = listItemView.findViewById(R.id.book_author);
        bookAuthor.setText(currentBook.getBookAuthor());

        TextView bookDescription = listItemView.findViewById(R.id.book_description);
        bookDescription.setText(currentBook.getBookDescription());

        return listItemView;
    }
}
