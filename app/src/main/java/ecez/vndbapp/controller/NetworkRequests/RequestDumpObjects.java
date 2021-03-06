package ecez.vndbapp.controller.NetworkRequests;

import android.app.ProgressDialog;
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

import ecez.vndbapp.controller.vndatabaseapp;
import ecez.vndbapp.model.DumpObject;
import ecez.vndbapp.model.SystemStatus;
import ecez.vndbapp.model.Tag;
import ecez.vndbapp.model.Trait;

/**
 * Created by Teng on 12/8/2016.
 */
public class RequestDumpObjects extends AsyncTask{
    private Context contextReference;
    private DumpObject[] l;
    private String line = null;
    private ProgressDialog dialogReference;
    private String URL, saveDir;
    private HashMap hashMap;

    public RequestDumpObjects(Context context, ProgressDialog dialog, String URL, String saveDir) {
        this.contextReference = context;
        this.dialogReference = dialog;
        this.URL = URL;
        this.saveDir = saveDir;
    }

    @Override
    protected void onPreExecute() {
        dialogReference.setMessage("Updating some data");
        dialogReference.show();
    }

    @Override
    protected Boolean doInBackground(Object[] params) {
        try {
            URLConnection conn;
            URL url = new URL(URL);
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
        hashMap = new HashMap<>();
        if (saveDir.equals("traitsMap")) {
            l = gson.fromJson(line, Trait[].class);
        } else {
            l = gson.fromJson(line, Tag[].class);
        }
        if (l == null) {
            Log.d("null","array is null");
            return false;
        }

        for (int x = 0; x<l.length;x++) {
            hashMap.put(l[x].getId(),l[x]);
        }

        if (saveDir.equals("traitsMap"))
            SystemStatus.getInstance().traitsMap = hashMap;
        else
            SystemStatus.getInstance().tagsMap = hashMap;

        File file = new File(contextReference.getDir("data", Context.MODE_PRIVATE), saveDir);

        if (file == null) {
            return false;
        }
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(hashMap);
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
        dialogReference.dismiss();
    }
}
