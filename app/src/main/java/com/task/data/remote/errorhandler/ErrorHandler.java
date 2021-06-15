package com.task.data.remote.errorhandler;

import android.net.ParseException;
import com.google.gson.JsonParseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import com.google.gson.stream.MalformedJsonException;
import com.task.data.remote.RemoteConstants;

public class ErrorHandler {

    public static String getErrorMessage(Throwable throwable){

        String errorMessage;

        if (throwable instanceof UnknownHostException)
        {
            errorMessage = "We can not find the server at "+ RemoteConstants.HOST_URL+" If that address is correct, here are three other things you can try :\n" +
                    "\n" +
                    "1) Try again later.\n" +
                    "2) Check your network connection.\n" +
                    "3) If you are connected, Check that Tutti Artist has internet permission.";
        }
        else if (throwable instanceof ConnectException)
        {
            errorMessage = "Host and Port combination not valid, Connection refused!";

        }
        else if(throwable instanceof SocketTimeoutException || throwable instanceof ConnectTimeoutException)
        {
            errorMessage = "Connection timeOut error!";
        }
        else if (throwable instanceof JsonParseException || throwable instanceof JSONException || throwable instanceof ParseException|| throwable instanceof MalformedJsonException)
        {
            errorMessage = "Parsing error! Something went wrong api is not responding properly.";
        }
        else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            errorMessage = "javax.net.ssl.SSLHandshakeException";
        }
        else if(throwable instanceof retrofit2.HttpException)
        {
            HttpResponse httpResponse = HttpStatusCode.getCodeInfo(((retrofit2.HttpException) throwable).code());
            errorMessage = httpResponse.getStatusMessage();
        }
        else
        {
            errorMessage = throwable.getMessage();
            if (errorMessage==null||errorMessage.length() <= 40)
            {
                errorMessage = "Something went wrong, please try again later.";
            }
        }
        return errorMessage;
    }
}
