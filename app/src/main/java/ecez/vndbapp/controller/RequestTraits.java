package ecez.vndbapp.controller;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import ecez.vndbapp.model.Trait;

/**
 * Created by Teng on 12/8/2016.
 */
public class RequestTraits extends AsyncTask{
    @Override
    protected Object doInBackground(Object[] params) {
        StringBuilder line = new StringBuilder();

        try {
            URLConnection conn;
            URL url = new URL("https://vndb.org/api/traits.json.gz");
            conn = url.openConnection();
            GZIPInputStream ginStream = new GZIPInputStream(conn.getInputStream());
            conn.setAllowUserInteraction(false);
            InputStreamReader urlStream = new InputStreamReader(ginStream);
            BufferedReader buffer = new BufferedReader(urlStream);
            while ( buffer.readLine() != null)
                line.append(buffer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (line.toString().equals(""))
            return false;

        String s = line.toString();
        Gson gson = new Gson();
        Trait[] l = gson.fromJson(s,Trait[].class);
        HashMap<Integer, Trait> traitHashMap = new HashMap<Integer, Trait>();

        for (int x = 0; x<l.length;x++) {
            traitHashMap.put(l[x].getId(),l[x]);
        }

        return true;

    }


    protected void onPostExecute(Boolean result) {

    }
}
