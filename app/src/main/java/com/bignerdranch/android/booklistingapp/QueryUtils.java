package com.bignerdranch.android.booklistingapp;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {


    public static final String LOG_TAG = QueryUtils.class.getName();


    private QueryUtils() {

    }


    public static List<Book> extractFeatureFromJson(String bookJson) {

        if (TextUtils.isEmpty(bookJson)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(bookJson);

            if (jsonResponse.getInt("totalItems") == 0) {
                return books;
            }

            JSONArray jsonArray = jsonResponse.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject bookObject = jsonArray.getJSONObject(i);

                JSONObject bookInfo = bookObject.getJSONObject("volumeInfo");

                String title = bookInfo.getString("title");

                JSONArray authorsArray = bookInfo.getJSONArray("authors");

                String authors = formatListOfAuthors(authorsArray);

                String description = bookInfo.getString("description");

                String bookUrl = bookInfo.getString("previewLink");

                Book book = new Book(title ,authors,description,bookUrl);

                books.add(book);
            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return books;
    }


            public static String formatListOfAuthors(JSONArray authorsList) throws JSONException {

              String authorsListInString = null;

                if (authorsList.length() == 0) {
                    return null;
                }
                for (int i = 0; i < authorsList.length(); i++){
                    if (i == 0) {
                        authorsListInString = authorsList.getString(0);
                    } else {
                        authorsListInString += ", " + authorsList.getString(i);
                    }
                }

                return authorsListInString;
            }


 public static List<Book> fetchBookData(String requestUrl){

     // Create URL object
     URL url = createUrl(requestUrl);

     // Perform HTTP request to the URL and receive a JSON response back
     String jsonResponse = null;
     try {

         jsonResponse = makeHttpRequest(url);
     }
     catch (IOException e) {
         Log.e(LOG_TAG, "Error closing input stream", e);
     }

     Log.i(LOG_TAG,"data fetched");

     // Extract relevant fields from the JSON response and create an {@link Event} object
     List<Book> books = extractFeatureFromJson(jsonResponse);

     // Return the {@link Event}

     return books ;
 }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}