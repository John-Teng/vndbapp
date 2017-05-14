package ecez.vndbapp.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.ReleaseAdapter;
import ecez.vndbapp.controller.Callbacks.DefaultCallback;
import ecez.vndbapp.controller.NetworkRequests.PopulateRelease;
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
        private int novelID;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.GRAY);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_release_list);
            intent = getIntent();
            novelID = intent.getIntExtra("NOVEL_ID",-1);

            recyclerView = (RecyclerView)findViewById(R.id.release_list_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            adapter = new ReleaseAdapter(releases, getApplicationContext(), recyclerView, novelID);
            recyclerView.setAdapter(adapter);

            makeReleaseRequest();
        }

    private void makeReleaseRequest() {
        PopulateRelease pr = new PopulateRelease(novelID, "basic,details,producers", new DefaultCallback() {
            @Override
            public void onSuccess(Object item) {
                Release[] releases = (Release []) item;
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
