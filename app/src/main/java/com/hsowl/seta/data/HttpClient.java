package com.hsowl.seta.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public String getData(String url) {
        String response = null;
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            con = (HttpURLConnection) (new URL(url).openConnection());
            con.setRequestMethod("GET");
            con.setDoInput(true);
            //con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null)
                buffer.append(line);

            is.close();
            con.disconnect();
            response = buffer.toString();
        } catch (Throwable t) {
            Log.d("HttpClient_Exception", Log.getStackTraceString(t));
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
                Log.d("HttpClient_Exception", Log.getStackTraceString(t));
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
                Log.d("HttpClient_Exception", Log.getStackTraceString(t));
            }
        }
        return response;
    }
}
