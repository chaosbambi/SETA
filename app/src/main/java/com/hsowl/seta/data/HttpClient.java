package com.hsowl.seta.data;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpClient {

    private static final String COOKIES_HEADER = "Set-Cookie";
    private static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    private HttpURLConnection con;


    public void storeCookiesFromConnection(){
        Map<String, List<String>> headerFields = con.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
    }

    public void addCookiesToConnection(){
        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
            con.setRequestProperty("Cookie", TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
        }
    }



    public String getData() {
        String response = null;
        InputStream is = null;

        try {
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

    public void initializeConnection(String url){
        try{
            con = (HttpURLConnection) (new URL(url).openConnection());
            con.setRequestMethod("GET");
            con.setDoInput(true);
        }catch(Exception e){
            Log.getStackTraceString(e);
        }
    }
}
