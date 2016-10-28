package ecez.vndbapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.endlessRecyclerViewScrollListener;
import ecez.vndbapp.controller.populateListItems;
import ecez.vndbapp.controller.recyclerAdapter;
import ecez.vndbapp.model.serverRequest;
import ecez.vndbapp.model.listItem;

public class tabFragment1 extends Fragment {
    public static RecyclerView recyclerView;
    private recyclerAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<listItem> loadedCards = new ArrayList<listItem>();
    private View view;
    private int pageCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab1, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(view.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        endlessRecyclerViewScrollListener endlessScrollListener = new endlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                new Thread() {
                    public void run() {
                        updateList();
                    }
                }.start();
            }
        };
        recyclerView.addOnScrollListener(endlessScrollListener);
        if (!vndatabaseapp.connectedToServer) {
            new Thread() {
                public void run() {
                    initialLoad();
                }
            }.start();
        }
        Log.d("Startup value",Boolean.toString(vndatabaseapp.connectedToServer));

        recyclerView.setLayoutManager(layoutManager);
        adapter = new recyclerAdapter(loadedCards, this.getContext());
        Log.d("Loaded Cards","The arraylist has " + Integer.toString(loadedCards.size()));
        recyclerView.setAdapter(adapter);

        return view;
    }

   public void initialLoad () {
       Thread a = new Thread() {
           public void run() {vndatabaseapp.loggedIn = serverRequest.login();}
       };
       a.start();
       try {
           a.join();
       } catch (InterruptedException f) { f.printStackTrace(); }
       updateList();
    }

    public void updateList () {
        final populateListItems l = new populateListItems(new ArrayList<listItem>(),pageCount);
        l.start();
        try {
            l.join();
        } catch (InterruptedException f) { f.printStackTrace(); }
        loadedCards.addAll(l.getList());
        Log.d("Loaded Cards Inner","The arraylist has " + Integer.toString(loadedCards.size()));
        pageCount ++;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(view.GONE);
                adapter.setData(loadedCards);
                adapter.notifyDataSetChanged();
            }
        });
    }
}