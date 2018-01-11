package ecez.vndbapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.ReleaseAdapter;
import ecez.vndbapp.controller.Callbacks.DefaultCallback;
import ecez.vndbapp.controller.NetworkRequests.PopulateRelease;
import ecez.vndbapp.model.Constants;
import ecez.vndbapp.model.Error;
import ecez.vndbapp.model.Release;

/**
 * Created by johnteng on 2017-05-13.
 */

public class ReleaseList extends AppCompatActivity {

    public static RecyclerView recyclerView;
    private ReleaseAdapter adapter;
    private Release[] releases = new Release[0];
    private Intent intent;
    private int mNovelID;
    private String mNovelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_list);
        intent = getIntent();
        mNovelID = intent.getIntExtra(Constants.INTENT_ID, -1);
        mNovelName = intent.getStringExtra(Constants.INTENT_NAME);

        recyclerView = (RecyclerView) findViewById(R.id.release_list_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ReleaseAdapter(releases, getApplicationContext(), recyclerView, mNovelID);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Releases");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        makeReleaseRequest();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed (){
        super.onBackPressed();
        finish();
    }

    private void makeReleaseRequest() {
        PopulateRelease pr = new PopulateRelease(mNovelID, "basic,details,producers", new DefaultCallback() {
            @Override
            public void onSuccess(Object item) {
                Release[] releases = (Release[]) item;
                adapter.setData(releases);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Error error, String errorMessage) {
                Log.d("AsyncTask FAILURE", errorMessage);
                if (error != null) {
                    Log.d("Error ID:", error.getId());
                    Log.d("Error Message:", error.getMsg());
                }
            }
        });
        pr.execute();
    }


}
