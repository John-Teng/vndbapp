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
    public static RecyclerView sNovelListRecyclerView;
    private VNRecyclerAdapter mRecyclerAdapter;
    private ProgressBar mProgressBar;
    private List<NovelData> mLoadedNovelDatas = new ArrayList<>();
    private View mNovelListView;
    private int mPageCount = 1, mSortParam = 0;
    private SwipeRefreshLayout mSwipeContainer;
    private ObserverSubject mObserverSubject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSortParam = getArguments().getInt("SORTPARAM");
        mNovelListView = inflater.inflate(R.layout.novel_list_fragment, container, false);
        mSwipeContainer = (SwipeRefreshLayout) mNovelListView.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadList(1);
            }
        });
        mSwipeContainer.setColorSchemeResources(R.color.colorPrimaryDark);

        mProgressBar = (ProgressBar) mNovelListView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(mNovelListView.VISIBLE);

        setupLayout();

        Log.d("Startup value",Boolean.toString(SystemStatus.getInstance().connectedToServer));
        mObserverSubject = SystemStatus.getInstance();
        mObserverSubject.registerObserver(this);
        return mNovelListView;
    }

    public void setupLayout () {
        sNovelListRecyclerView = (RecyclerView) mNovelListView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager;
        EndlessRecyclerViewScrollListener endlessScrollListener;
        if (SystemStatus.getInstance().displayMethod == 0) {
            layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerAdapter = new ListRecyclerAdapter(mLoadedNovelDatas, this.getContext(), mSortParam);
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
            mRecyclerAdapter = new GridRecyclerAdapter(mLoadedNovelDatas, this.getContext(), mSortParam);
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

        sNovelListRecyclerView.setLayoutManager(layoutManager);

        sNovelListRecyclerView.addOnScrollListener(endlessScrollListener);
        sNovelListRecyclerView.setLayoutManager(layoutManager);
        Log.d("Loaded Cards","The arraylist has " + Integer.toString(mLoadedNovelDatas.size()));
        sNovelListRecyclerView.setAdapter(mRecyclerAdapter);

    }

    public void loadList (int pageNum) {
        if (pageNum!=0) {
            mPageCount = pageNum;
        }
        PopulateListItems l = new PopulateListItems(mPageCount, mSortParam);
        l.callback = new ListCallback () {
            @Override
            public void returnList(Object [] array) { //This is run on a background thread
                List<NovelData> novels = new ArrayList<>(Arrays.asList((NovelData[]) array)); //downcast and convert to list
                Log.d("callback","onSuccess callback being called ");
                Log.d("callback","The current page is " + Integer.toString(mPageCount) );
                if (mPageCount == 1) {
                    mLoadedNovelDatas.clear();
                    Log.d("callback","There are now " + mLoadedNovelDatas.size() + " items in the list");
                }
                mLoadedNovelDatas.addAll(novels);
                mPageCount++;
            }
            @Override
            public void onSuccessUI() { //This is run on UI thread
                //mLoadedNovelDatas is being modified by reference in the AsyncTask
                mProgressBar.setVisibility(View.GONE);
                mRecyclerAdapter.setData(mLoadedNovelDatas);
                mRecyclerAdapter.notifyDataSetChanged();
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
        mRecyclerAdapter = null;
        sNovelListRecyclerView = null;
    }

    @Override
    public void onDestroy() {
        mObserverSubject.removeObserver(this);
        super.onDestroy();
    }

    public void onDataChanged() {
        resetViewItems();
        setupLayout();
        Log.d("REFRESH","should refresh fragment");
    }

}