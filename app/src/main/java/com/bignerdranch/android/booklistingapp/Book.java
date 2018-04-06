package com.bignerdranch.android.booklistingapp;

/**
 * Created by ismail on 05/03/18.
 */

public class Book {

    private String mBookTitle;

    private String mBookAuthor;

    private String mBookDescription;


    public Book(String bookTitle, String bookAuthor, String bookDescription) {
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mBookDescription = bookDescription;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public String getBookAuthor() {
        return mBookAuthor;
    }

    public String getBookDescription() {
        return mBookDescription;
    }

}
