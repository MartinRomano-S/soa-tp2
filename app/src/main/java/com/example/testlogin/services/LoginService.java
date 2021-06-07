package com.example.testlogin.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.example.testlogin.R;
import com.example.testlogin.interfaces.Asyncronable;
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

public class LoginService extends AsyncTask<String, Void, JSONObject> {

    Context c;
    Asyncronable asyncActivityUI;
    AlertDialog.Builder dialog;
    String endpoint;
    SOAAPIallowedMethodsEnum method;
    JSONObject data;

    public LoginService(Context c, Asyncronable asyncActivityUI, int endpoint, SOAAPIallowedMethodsEnum method, JSONObject data) {
        this.c = c;
        this.asyncActivityUI = asyncActivityUI;
        this.endpoint = c.getString(R.string.api_base_url) + c.getString(endpoint);
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
        dialog = new AlertDialog.Builder(c);
        dialog.setTitle("Bienvenido");
        try {
            dialog.setMessage("Success: " + response.getString("success") + "\nMessage: " + response.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.create().show();
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
        byte[] input = data.toString().getBytes("utf-8");
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
