package ecez.vndbapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private ProgressBar pb;
    private ArrayList<ListItem> loadedCards = new ArrayList<>();
    private View view;
    private String sortParam;
    private int pageCount = 1;
    private SwipeRefreshLayout mSwipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sortParam = getArguments().getString("SORTPARAM");
        view = inflater.inflate(R.layout.top_novels_fragment, container, false);
        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadList(true);
            }
        });
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        pb = (ProgressBar)view.findViewById(R.id.progressBar);
        pb.setVisibility(view.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        EndlessRecyclerViewScrollListener endlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                new Thread() {
                    public void run() {
                        if (vndatabaseapp.loggedIn)
                            loadList(false);
                    }
                }.start();
            }
        };
        recyclerView.addOnScrollListener(endlessScrollListener);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(loadedCards, this.getContext(), getActivity());
        Log.d("Loaded Cards","The arraylist has " + Integer.toString(loadedCards.size()));
        recyclerView.setAdapter(adapter);

        Log.d("Startup value",Boolean.toString(vndatabaseapp.connectedToServer));

        return view;
    }

    public void loadList (boolean firstPage) {
        if (firstPage)
            pageCount = 1;
        PopulateListItems l = new PopulateListItems(new ArrayList<ListItem>(),pageCount,sortParam,pb,adapter,mSwipeContainer);
        l.execute();
        Log.d("Async Task","Finished request, getting data");
        pageCount ++;
    }


    }