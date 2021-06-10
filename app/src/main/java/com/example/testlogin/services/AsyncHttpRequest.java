package com.example.testlogin.services;

import android.hardware.SensorManager;
import android.os.AsyncTask;

import com.example.testlogin.interfaces.Asyncronable;
import com.example.testlogin.utils.Configuration;
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

/**
 * Clase genérica para gestionar request en una tarea en segundo plano
 */
public class AsyncHttpRequest extends AsyncTask<String, Void, JSONObject> {

    private Asyncronable<JSONObject> asyncActivityUI;
    private String endpoint;
    private SOAAPIallowedMethodsEnum method;
    private JSONObject data;

    public AsyncHttpRequest(Asyncronable<JSONObject> asyncActivityUI, String endpoint, SOAAPIallowedMethodsEnum method, JSONObject data) {
        this.asyncActivityUI = asyncActivityUI;
        this.endpoint = Configuration.API_BASE_URL + endpoint;
        this.method = method;
        this.data = data;
    }

    @Override
    protected void onPreExecute() { asyncActivityUI.showProgress(""); }

    @Override
    protected JSONObject doInBackground(String... strings) {

        InputStream in;
        OutputStream out;

        try {
            URL url = new URL(endpoint);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            addHeaders(urlConnection);

            out = new BufferedOutputStream(urlConnection.getOutputStream());

            writeRequest(out);
            out.flush();
            urlConnection.connect();

            int status = urlConnection.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK)
                in = urlConnection.getInputStream();
            else
                in = urlConnection.getErrorStream();

            JSONObject response = readResponse(in);
            in.close();
            out.close();
            urlConnection.disconnect();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        asyncActivityUI.afterRequest(response);
        asyncActivityUI.hideProgress();
    }

    private void addHeaders(HttpURLConnection urlConnection) throws ProtocolException {
        urlConnection.setReadTimeout(Configuration.REQUEST_READ_TIMEOUT);
        urlConnection.setConnectTimeout(Configuration.REQUEST_CONNECTION_TIMEOUT);
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
