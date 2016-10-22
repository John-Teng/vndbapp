package ecez.vndbapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.recyclerAdapter;
import ecez.vndbapp.controller.serverRequest;
import ecez.vndbapp.model.listItem;

public class tabFragment1 extends Fragment {
    private RecyclerView recyclerView;
    private recyclerAdapter adapter;
    private ArrayList<listItem> list = new ArrayList<>();
    ArrayList<listItem> newList = new ArrayList<>();
    private String cardDataString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list.add(new listItem("test", 9.9,5, "http://vignette2.wikia.nocookie.net/typemoon/images/4/4e/FGO_Archer_Emiya.png",1));
        new Thread() {
            public void run() {
                Thread a = new Thread() {
                    public void run() {
                        vndatabaseapp.loggedIn = serverRequest.login();
                        Log.d("Login Attempt","Attempted to login");
                    }
                };
                a.start();
                try {
                    a.join();
                } catch (InterruptedException f) {
                    f.printStackTrace();
                }

                Thread b = new Thread() {
                    public void run() {
                        if (vndatabaseapp.loggedIn == true)
                            cardDataString = serverRequest.writeToServer("get", "vn", "basic,stats,details", "(released > \"1945\")", "{\"page\":1,\"results\":15,\"sort\":\"rating\",\"reverse\":true}");
                        else
                            Log.d("Connection failure", "Cannot connect to server");
                    }
                };
                b.start();
                try {
                    b.join();
                } catch (InterruptedException f) {
                    f.printStackTrace();
                }
                Log.d("JSON Response",cardDataString);
                populateData();
            }
        }.start();

        View view = inflater.inflate(R.layout.tab1, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new recyclerAdapter(list, this.getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void populateData () { //temporary fucntion to populate an arraylist, in the future, this will be handled in the API parser class
//        list.add(new listItem("#1 - Muv-Luv Alternative", "9.21 (excellent)","Very Long (> 50 hours)", R.mipmap.ic_launcher));
//        list.add(new listItem("#2 - Steins;Gate", "9.02 (excellent)","Long (30 - 50 hours)",  R.mipmap.ic_launcher));
//        list.add(new listItem("#3 - Baldr Sky Dive2 'Recordare'", "8.83 (very good)","Very Long (> 50 hours)", R.mipmap.ic_launcher));
//        list.add(new listItem("#4 - White Album 2 ~Closing Chapter~", "8.77 (very good)","Very Long (> 50 hours)",  R.mipmap.ic_launcher));
//        list.add(new listItem("#5 - Umineko no Naku Koro ni", "8.76 (very good)","Very Long (> 50 hours)",  R.mipmap.ic_launcher));
        Gson gson = new Gson();
        String dataString = cardDataString.substring(8,cardDataString.length());
        Log.d("Modified Response 1",dataString);
//        Log.d("Modified Response 1",dataString.substring(0,3000));
//        Log.d("Modified Response 2",dataString.substring(3000,6000));
//        Log.d("Modified Response 3",dataString.substring(6000,9000));
//        Log.d("Modified Response 3",dataString.substring(9000,12000));
//        Log.d("Modified Response 4",dataString.substring(12000,13351));

        int numberOfResponses = 0;
        JSONArray jsonResponse = null;
        try {
            JSONObject returnObject = new JSONObject(dataString);
            numberOfResponses = returnObject.getInt("num");
            jsonResponse = returnObject.getJSONArray("items");
            Log.d("items json",jsonResponse.toString());
            Log.d("number of itmes",Integer.toString(numberOfResponses));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //String j = jsonResponse.toString().substring(1,jsonResponse.toString().length()-1); //peel off the outer square braces
        Log.d("json",jsonResponse.toString());
        listItem[] l = gson.fromJson(jsonResponse.toString(),listItem[].class);
        newList = new ArrayList<listItem>(Arrays.asList(l));
        int y = 1;
        for (listItem x:newList) {
            x.rank = y;
            y ++;
        }
//        if (j!=null && numberOfResponses!=0) {
//            for (int x = 0; x < numberOfResponses; x++) {
//                listItem item = gson.fromJson(j, listItem.class);
//                newList.add(item);
//            }
//        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setData(newList);
                adapter.notifyDataSetChanged();
            }
        });
    }
}