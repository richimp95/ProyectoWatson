package com.ibm.watson.developer_cloud.android.ConeccionClima;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by richi on 09/05/2017.
 */

public class ConeccionClima {

    final static String BASE_URL =
            "http://uvgproyectos.esaludgt.org/web/Api/Codigos";

    final static String PARAM_QUERY = "diagnostico";


    /**
     * Builds the URL used to query .
     *
     * @param SearchQuery The keyword that will be queried for.
     * @return The URL to use to query.
     */
    public static URL buildUrl(String SearchQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, SearchQuery)
                //.appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
