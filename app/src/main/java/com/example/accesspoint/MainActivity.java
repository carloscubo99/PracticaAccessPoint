package com.example.accesspoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static Context appContext;

    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            //esto se lanza en el execute
            Log.e("--uva", "doInBackGround->ESTOY EN OTRO HILO");
            consultaHTTP();
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(MainActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MiTareaAsincrona().execute();

        /*appContext = getApplicationContext();
        WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;
        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            Log.e("WIFI", wifiInfo.getSSID());
        }*/

        //consultaHTTP();

    }





    public void consultaHTTP(){
        //GET request
        URL url = null;
        try {
            url = new URL("http://www.android.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String res = readStream(in);
                Log.e("--respuestaServidor", res);
                //Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //readStream(in);
        } finally {
            urlConnection.disconnect();
        }

    }

    /*public String readString(InputStream input) throws IOException {
        byte[] bytes = new byte[50];
        input.read(bytes);
        return new String(bytes);
    }*/

    public String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

}

