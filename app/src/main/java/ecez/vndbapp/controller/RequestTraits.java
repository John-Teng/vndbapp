package ecez.vndbapp.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import ecez.vndbapp.model.Trait;

/**
 * Created by Teng on 12/8/2016.
 */
public class RequestTraits extends AsyncTask{
    Context contextReference;
    Trait[] l;
    String line = null;


    public RequestTraits (Context context) {
        this.contextReference = context;
    }

    @Override
    protected void onPreExecute() {
        Log.d("AsyncTask","Pre-executing");
    }

    @Override
    protected Boolean doInBackground(Object[] params) {
        try {
            URLConnection conn;
            URL url = new URL("https://vndb.org/api/traits.json.gz");
            conn = url.openConnection();
            GZIPInputStream ginStream = new GZIPInputStream(conn.getInputStream());
            InputStreamReader urlStream = new InputStreamReader(ginStream);
            BufferedReader buffer = new BufferedReader(urlStream);
            line = buffer.readLine();
            Log.d("First Line",line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (line == null)
            return false;

        final Gson gson = new Gson();
        Thread a = new Thread() {
            public void run() {l = gson.fromJson(line,Trait[].class);
            }
        };
        a.start();
        try {
            a.join();
        } catch (InterruptedException f) { f.printStackTrace(); }
        if (l == null) {
            Log.d("null","array is null");
            return false;
        }

        HashMap<Integer, Trait> traitHashMap = new HashMap<Integer, Trait>();
        for (int x = 0; x<l.length;x++) {
            traitHashMap.put(l[x].getId(),l[x]);
        }

        File file = new File(contextReference.getDir("data", Context.MODE_PRIVATE), "traitsMap");

        if (file == null) {
            return false;
        }
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(traitHashMap);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("hashmap","Successfully saved to internal storage!");
        return true;
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.d("AsyncTask","Post-executing");
    }
}
