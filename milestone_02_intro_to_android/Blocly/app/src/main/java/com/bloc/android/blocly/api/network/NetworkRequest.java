package com.bloc.android.blocly.api.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Wayne on 1/9/2015.
 */
//establish a template result type
public abstract class NetworkRequest<Result> {

    //  make each request responsible for reporting errors to its callers
    public static final int ERROR_IO = 1;
    public static final int ERROR_MALFORMED_URL = 2;

    private int errorCode;

    protected void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    // method which accesses the Internet, retrieves the result and returns it in the anticipated form
    public abstract Result performRequest();

    // help subclasses make GET HTTP requests of any URL. This method ultimately returns an InputStream
    protected InputStream openStream(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            setErrorCode(ERROR_MALFORMED_URL);
            return null;
        }
        InputStream inputStream = null;
        try {
            // creates the network connection required to recover data found at that address
            inputStream = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            setErrorCode(ERROR_IO);
            return null;
        }
        return inputStream;
    }
}
