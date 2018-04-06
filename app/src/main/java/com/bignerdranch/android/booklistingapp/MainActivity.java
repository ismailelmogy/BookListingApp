package com.bignerdranch.android.booklistingapp;

import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String BOOK_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=search+";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private EditText mSearchEditText;
    private Button mSearchButton;
    BookAdapter mAdapter;
    private static final int BOOK_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchEditText = findViewById(R.id.editText);
        mSearchButton = findViewById(R.id.btnSearch);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSearchEditText.getText().toString().length() > 0) {

                    if (isNetworkAvailable()) {
                        restartLoader();
                    } else {
                        Toast.makeText(MainActivity.this, "No Internet Connection....Please connect the internet",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Enter keyword that you search for ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(mAdapter);

    }


    private String getUrlForHttpRequest() {
        String formatUserInput = getUserInput().trim().replaceAll("\\s+", "+");
        String url = BOOK_REQUEST_URL + formatUserInput;
        return url;
    }

    public String getUserInput() {
        return mSearchEditText.getText().toString();
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new BookLoader(getApplicationContext(), getUrlForHttpRequest());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }

    public void restartLoader() {
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}