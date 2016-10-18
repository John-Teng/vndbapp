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

import org.json.JSONObject;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.recyclerAdapter;
import ecez.vndbapp.controller.serverRequest;
import ecez.vndbapp.model.listItem;

public class tabFragment1 extends Fragment {
    private RecyclerView recyclerView;
    private recyclerAdapter adapter;
    private ArrayList<listItem> list = new ArrayList<>();
    private String cardDataString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new Thread() {
            public void run() {
                Thread t = new Thread() {
                    public void run() {
                        vndatabaseapp.connectedToServer = serverRequest.connect();
                    }
                };
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (vndatabaseapp.connectedToServer == true)
                    cardDataString = serverRequest.writeToServer("get", "vn", "basic  ", "(rating = 10)", null);
                else
                    Log.d("Connection failure", "Cannot connect to server");
            }
        }.start();

        View view = inflater.inflate(R.layout.tab1, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        fillArraylist();
        recyclerView.setAdapter(new recyclerAdapter(list, this.getContext()));

        return view;
    }


    private ArrayList<listItem> fillArraylist () { //temporary fucntion to populate an arraylist, in the future, this will be handled in the API parser class
        list.add(new listItem("#1 - Muv-Luv Alternative", "9.21 (excellent)","Very Long (> 50 hours)", R.mipmap.ic_launcher));
        list.add(new listItem("#2 - Steins;Gate", "9.02 (excellent)","Long (30 - 50 hours)",  R.mipmap.ic_launcher));
        list.add(new listItem("#3 - Baldr Sky Dive2 'Recordare'", "8.83 (very good)","Very Long (> 50 hours)", R.mipmap.ic_launcher));
        list.add(new listItem("#4 - White Album 2 ~Closing Chapter~", "8.77 (very good)","Very Long (> 50 hours)",  R.mipmap.ic_launcher));
        list.add(new listItem("#5 - Umineko no Naku Koro ni", "8.76 (very good)","Very Long (> 50 hours)",  R.mipmap.ic_launcher));
        return list;
    }
}