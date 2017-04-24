package ecez.vndbapp.controller;

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
import java.util.List;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.ListRecyclerAdapter;
import ecez.vndbapp.controller.Callbacks.ListCallback;
import ecez.vndbapp.controller.NetworkRequests.PopulateListItems;
import ecez.vndbapp.model.Error;
import ecez.vndbapp.model.ListItem;

public class NovelListFragment extends Fragment {
    public static RecyclerView recyclerView;
    private ListRecyclerAdapter adapter;
    private ProgressBar pb;
    private List<ListItem> mLoadedListItems = new ArrayList<>();
    private View view;
    private String sortParam;
    private int pageCount = 1;
    private SwipeRefreshLayout mSwipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sortParam = getArguments().getString("SORTPARAM");
        view = inflater.inflate(R.layout.novel_list_fragment, container, false);
        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadList(1);
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
                            loadList(0);
                    }
                }.start();
            }
        };
        recyclerView.addOnScrollListener(endlessScrollListener);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListRecyclerAdapter(mLoadedListItems, this.getContext(), getActivity());
        Log.d("Loaded Cards","The arraylist has " + Integer.toString(mLoadedListItems.size()));
        recyclerView.setAdapter(adapter);

        Log.d("Startup value",Boolean.toString(vndatabaseapp.connectedToServer));

        return view;
    }

    public void loadList (int pageNum) {
        if (pageNum!=0) {
            pageCount = pageNum;
        }
        PopulateListItems l = new PopulateListItems(pageCount,sortParam);
        l.callback = new ListCallback() {
            @Override
            public void returnList(List<ListItem> list) { //This is run on a background thread
                Log.d("callback","onSuccess callback being called ");
                Log.d("callback","The current page is " + Integer.toString(pageCount) );
                if (pageCount == 1) {
                    mLoadedListItems.clear();
                    Log.d("callback","There are now " + mLoadedListItems.size() + " items in the list");
                }
                mLoadedListItems.addAll(list);
                pageCount ++;
            }
            @Override
            public void onSuccessUI() { //This is run on UI thread
                //mLoadedListItems is being modified by reference in the AsyncTask
                pb.setVisibility(View.GONE);
                adapter.setData(mLoadedListItems);
                adapter.notifyDataSetChanged();
                mSwipeContainer.setRefreshing(false);
            }
            @Override
            public void onFailure(Error error, String errorMessage) {
                Log.d("AsyncTask FAILURE",errorMessage);
                if (error!=null){
                    Log.d("Error ID:",error.getId());
                    Log.d("Error Message:",error.getMsg());
                }
            }
        };

        l.execute();
        Log.d("Async Task","Finished request, getting data");
    }

}