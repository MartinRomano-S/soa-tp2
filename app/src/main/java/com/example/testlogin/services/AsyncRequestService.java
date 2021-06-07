package com.example.testlogin.services;

import android.os.AsyncTask;

import com.example.testlogin.interfaces.AsyncronableRequest;
import com.example.testlogin.utils.SOAAPIallowedMethodsEnum;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AsyncRequestService extends AsyncTask<String, Void, JSONObject> {

    private AsyncronableRequest asyncActivityUI;
    private String endpoint;
    private SOAAPIallowedMethodsEnum method;
    private JSONObject data;
    private final String API_BASE_URL = "http://so-unlam.net.ar/api/api/";

    public AsyncRequestService(AsyncronableRequest asyncActivityUI, String endpoint, SOAAPIallowedMethodsEnum method, JSONObject data) {
        this.asyncActivityUI = asyncActivityUI;
        this.endpoint = API_BASE_URL + endpoint;
        this.method = method;
        this.data = data;
    }

    @Override
    protected void onPreExecute() { asyncActivityUI.toggleProgressBar(true); }

    @Override
    protected JSONObject doInBackground(String... strings) {

        InputStream in;
        OutputStream out;

        try {
            URL url = new URL(endpoint);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            addHeaders(urlConnection);
            urlConnection.connect();

            out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeRequest(out);
            out.close();

            int status = urlConnection.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK)
                in = urlConnection.getInputStream();
            else
                in = urlConnection.getErrorStream();

            JSONObject response = readResponse(in);
            in.close();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        asyncActivityUI.showResponseMessage(response);
        asyncActivityUI.toggleProgressBar(false);
    }

    private void addHeaders(HttpURLConnection urlConnection) throws ProtocolException {
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(20000);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(method.toString());
        urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        urlConnection.setRequestProperty("Accept", "application/json");
    }

    private void writeRequest(OutputStream out) throws IOException {
        byte[] input = data.toString().getBytes(StandardCharsets.UTF_8);
        out.write(input, 0, input.length);
    }

    private JSONObject readResponse(InputStream in) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null)
            builder.append(line);

        reader.close();
        return new JSONObject(builder.toString());
    }
}
