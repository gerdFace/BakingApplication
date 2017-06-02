package com.hudboyz.android.bakingapplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/* REVIEW: TIGHTEN UP COMMENT--
 * Not being used? What's it doing here?
 */
public class NetworkCaller {

    public static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public String getResponseFromHttpUrl() {
        try {
            URL recipeUrl = new URL(RECIPE_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) recipeUrl.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(1000);
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            return scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
            return "cannot connect";
        }
    }
}
