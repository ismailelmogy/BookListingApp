package com.bignerdranch.android.booklistingapp;

/**
 * Created by ismail on 05/03/18.
 */

public class Book {

    private String mBookTitle;

    private String mBookAuthor;

    private String mBookDescription;

    private String mBookUrl;


    public Book(String bookTitle, String bookAuthor, String bookDescription,String bookUrl) {
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mBookDescription = bookDescription;
        mBookUrl= bookUrl;
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

    public String getBookUrl() {
        return mBookUrl;
    }
}
