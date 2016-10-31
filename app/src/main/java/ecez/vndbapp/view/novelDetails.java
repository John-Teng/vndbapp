package ecez.vndbapp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.populateListItems;
import ecez.vndbapp.controller.populateNovelDetails;
import ecez.vndbapp.model.detailsData;
import ecez.vndbapp.model.listItem;
import ecez.vndbapp.model.serverRequest;

public class novelDetails extends AppCompatActivity {

    TextView title, developer, votes, rating, popularity, length, languages, platforms, description;
    ImageView icon;
    detailsData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_details);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.GRAY);

        title = (TextView)findViewById(R.id.quickstats_title);
        developer = (TextView) findViewById(R.id.quickstats_developer);
        votes = (TextView) findViewById(R.id.quickstats_votes);
        rating = (TextView)findViewById(R.id.quickstats_rating);
        popularity = (TextView)findViewById(R.id.quickstats_popularity);
        length = (TextView)findViewById(R.id.quickstats_length);
        languages = (TextView)findViewById(R.id.languages);
        platforms = (TextView)findViewById(R.id.platforms);
        description = (TextView)findViewById(R.id.description);
        icon = (ImageView) findViewById(R.id.novel_icon);
        Intent intent = getIntent();

        int id = Integer.parseInt(intent.getStringExtra("NOVEL_ID"));
        Log.d("id",Integer.toString(id));
        loadData(id);

    }

    private void loadData (int id) {
        final populateNovelDetails d = new populateNovelDetails(id);
        d.start();
        try {
            d.join();
        } catch (InterruptedException f) { f.printStackTrace(); }

        Thread a = new Thread() {
            public void run() { data = d.getData();}
        };
        a.start();
        try {
            a.join();
        } catch (InterruptedException f) { f.printStackTrace(); }
        title.setText(data.getTitle());
        votes.setText(Integer.toString(data.getVoteCount()));
        rating.setText(data.getRating());
        popularity.setText(Double.toString(data.getPopularity()) + "%");
        length.setText(data.getLength());
        String [] l = data.getLanguages();
        StringBuilder ll = new StringBuilder();
        for (int x = 0; x<l.length; x++) {
            ll.append(l[x]);
            ll.append(" ");
        }
        languages.setText(ll.toString());
        String [] p = data.getPlatforms();
        StringBuilder pp = new StringBuilder();
        for (int x = 0; x<p.length; x++) {
            pp.append(p[x]);
            pp.append(" ");
        }
        platforms.setText(pp.toString());
        description.setText(data.getDescription());
        Picasso.with(getApplicationContext()).load(data.getImage()).fit().into(icon);

    }
}
