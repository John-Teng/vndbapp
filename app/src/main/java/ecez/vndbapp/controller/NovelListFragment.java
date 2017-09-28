package ecez.vndbapp.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.GridRecyclerAdapter;
import ecez.vndbapp.controller.Adapters.ListRecyclerAdapter;
import ecez.vndbapp.controller.Adapters.VNRecyclerAdapter;
import ecez.vndbapp.controller.Callbacks.ListCallback;
import ecez.vndbapp.controller.NetworkRequests.PopulateListItems;
import ecez.vndbapp.model.Error;
import ecez.vndbapp.model.NovelData;
import ecez.vndbapp.model.SystemStatus;

public class NovelListFragment extends Fragment implements CustomObserver {
    public static RecyclerView recyclerView;
    private VNRecyclerAdapter adapter;
    private ProgressBar pb;
    private List<NovelData> mLoadedNovelDatas = new ArrayList<>();
    private View view;
    private String sortParam;
    private int pageCount = 1;
    private SwipeRefreshLayout mSwipeContainer;
    private ObserverSubject observerSubject;

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
        mSwipeContainer.setColorSchemeResources(R.color.colorPrimaryDark);

        pb = (ProgressBar)view.findViewById(R.id.progressBar);
        pb.setVisibility(view.VISIBLE);

        setupLayout();

        Log.d("Startup value",Boolean.toString(SystemStatus.getInstance().connectedToServer));
        observerSubject = SystemStatus.getInstance();
        observerSubject.registerObserver(this);
        return view;
    }

    public void setupLayout () {
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager;
        EndlessRecyclerViewScrollListener endlessScrollListener;
        if (SystemStatus.getInstance().displayMethod == 0) {
            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new ListRecyclerAdapter(mLoadedNovelDatas, this.getContext());
            endlessScrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager)layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    new Thread() {
                        public void run() {
                            if (SystemStatus.getInstance().loggedIn)
                                loadList(0);
                        }
                    }.start();
                }
            };
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 2);
            adapter = new GridRecyclerAdapter(mLoadedNovelDatas, this.getContext());
            endlessScrollListener = new EndlessRecyclerViewScrollListener((GridLayoutManager) layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    new Thread() {
                        public void run() {
                            if (SystemStatus.getInstance().loggedIn)
                                loadList(0);
                        }
                    }.start();
                }
            };
        }

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(endlessScrollListener);
        recyclerView.setLayoutManager(layoutManager);
        Log.d("Loaded Cards","The arraylist has " + Integer.toString(mLoadedNovelDatas.size()));
        recyclerView.setAdapter(adapter);

    }

    public void loadList (int pageNum) {
        if (pageNum!=0) {
            pageCount = pageNum;
        }
        PopulateListItems l = new PopulateListItems(pageCount,sortParam);
        l.callback = new ListCallback () {
            @Override
            public void returnList(Object [] array) { //This is run on a background thread
                List<NovelData> novels = new ArrayList<>(Arrays.asList((NovelData[]) array)); //downcast and convert to list
                Log.d("callback","onSuccess callback being called ");
                Log.d("callback","The current page is " + Integer.toString(pageCount) );
                if (pageCount == 1) {
                    mLoadedNovelDatas.clear();
                    Log.d("callback","There are now " + mLoadedNovelDatas.size() + " items in the list");
                }
                mLoadedNovelDatas.addAll(novels);
                pageCount ++;
            }
            @Override
            public void onSuccessUI() { //This is run on UI thread
                //mLoadedNovelDatas is being modified by reference in the AsyncTask
                pb.setVisibility(View.GONE);
                adapter.setData(mLoadedNovelDatas);
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

    public void resetViewItems(){
        adapter = null;
        recyclerView = null;
    }

    @Override
    public void onDestroy() {
        observerSubject.removeObserver(this);
        super.onDestroy();
    }

    public void onDataChanged() {
        resetViewItems();
        setupLayout();
        Log.d("REFRESH","should refresh fragment");
    }

}