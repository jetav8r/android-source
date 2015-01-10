package com.bloc.android.blocly.api.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wayne on 1/9/2015.
 */
public class GetFeedsNetworkRequest extends NetworkRequest {
    // store each feed's address
    String [] feedUrls;
    private static final Pattern TITLE_TAG =
            Pattern.compile("<title>(.*)</title>", Pattern.CASE_INSENSITIVE| Pattern.DOTALL);


    public GetFeedsNetworkRequest(String... feedUrls) {
        this.feedUrls = feedUrls;
    }

    // override the mandatory abstract method declared in NetworkRequest
    @Override
    public Object performRequest() {
        Log.i ("# of items: ", "" +feedUrls.length);
        for (String feedUrlString : feedUrls) {
            Log.i ("URL of feed: ", feedUrlString );
            /*try {
                //Log.i("Title of feed: ", title);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            InputStream inputStream = openStream(feedUrlString);
            if (inputStream == null) {
                return null;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                // After we open a connection to the feed, we begin reading it
                String line = bufferedReader.readLine();
                while (line != null) {
                    Log.v(getClass().getSimpleName(), "Line: " + line);

                    line = bufferedReader.readLine();
                    // extract the title
                    try {
                        Matcher matcher = TITLE_TAG.matcher(line);
                        if (matcher.find()) {
                    /* replace any occurrences of whitespace (which may
                     * include line feeds and other uglies) as well
                     * as HTML brackets with a space */
                            //return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
                            String title = matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
                            Log.i("Title of RSS feed: ", title);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Close the reader and its stream
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                setErrorCode(ERROR_IO);
                return null;
            }
        }
        return null;
    }
}
