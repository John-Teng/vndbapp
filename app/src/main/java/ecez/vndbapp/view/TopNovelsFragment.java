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
import ecez.vndbapp.controller.EndlessRecyclerViewScrollListener;
import ecez.vndbapp.controller.PopulateListItems;
import ecez.vndbapp.controller.RecyclerAdapter;
import ecez.vndbapp.model.ListItem;

public class TopNovelsFragment extends Fragment {
    public static RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<ListItem> loadedCards = new ArrayList<ListItem>();
    private View view;
    private int pageCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.top_novels_fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(view.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        EndlessRecyclerViewScrollListener endlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                new Thread() {
                    public void run() {
                        if (vndatabaseapp.loggedIn)
                            updateList();
                    }
                }.start();
            }
        };
        recyclerView.addOnScrollListener(endlessScrollListener);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(loadedCards, this.getContext(), getActivity());
        Log.d("Loaded Cards","The arraylist has " + Integer.toString(loadedCards.size()));
        recyclerView.setAdapter(adapter);

//        if (!vndatabaseapp.connectedToServer && !vndatabaseapp.loggedIn) {
//            new Thread() {
//                public void run() {
//                    initialLoad();
//                }
//            }.start();
//        } else {
        if (vndatabaseapp.loggedIn) {
            updateList();
        }
//        }
        Log.d("Startup value",Boolean.toString(vndatabaseapp.connectedToServer));


        return view;
    }

//   public void initialLoad () {
//       Thread a = new Thread() {
//           public void run() {vndatabaseapp.loggedIn = ServerRequest.login();}
//       };
//       a.start();
//       try {
//           a.join();
//       } catch (InterruptedException f) { f.printStackTrace(); }
//       updateList();
//    }

    public void updateList () {
        final PopulateListItems l = new PopulateListItems(new ArrayList<ListItem>(),pageCount);
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