package ecez.vndbapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import at.blogc.android.views.ExpandableTextView;
import ecez.vndbapp.R;
import ecez.vndbapp.controller.imagePagerAdapter;
import ecez.vndbapp.controller.pictureViewerAdapter;
import ecez.vndbapp.controller.populateNovelDetails;
import ecez.vndbapp.model.detailsData;
import ecez.vndbapp.model.novelScreenShot;
import ecez.vndbapp.model.pictureViewerImage;

public class novelDetails extends AppCompatActivity {

    pictureViewerAdapter adapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView title, developer, votes, rating, popularity, length, languages, platforms;
    Button expandButton;
    ExpandableTextView description;
    ImageView icon;
    ViewPager imagePager;
    detailsData data;
    ArrayList<novelScreenShot> pictures = new ArrayList<novelScreenShot>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_details);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.GRAY);

        toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        title = (TextView)findViewById(R.id.appbar_title);
        developer = (TextView) findViewById(R.id.appbar_subtitle);
        votes = (TextView) findViewById(R.id.quickstats_votes);
        rating = (TextView)findViewById(R.id.quickstats_rating);
        popularity = (TextView)findViewById(R.id.quickstats_popularity);
        length = (TextView)findViewById(R.id.quickstats_length);
        languages = (TextView)findViewById(R.id.languages);
        platforms = (TextView)findViewById(R.id.platforms);
        description = (ExpandableTextView)findViewById(R.id.description);
        icon = (ImageView) findViewById(R.id.novel_icon);
        //recyclerView = (RecyclerView)findViewById(R.id.picture_viewer);
        imagePager = (ViewPager) findViewById(R.id.imagePager);
        expandButton = (Button) this.findViewById(R.id.expand_button);
        description.setInterpolator(new OvershootInterpolator());
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.toggle();
                expandButton.setText(description.isExpanded() ? "Read More" : "Read Less");
            }
        });

        Intent intent = getIntent();
        //adapter = new pictureViewerAdapter(pictures, getApplicationContext());
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapter);

        int id = Integer.parseInt(intent.getStringExtra("NOVEL_ID"));
        Log.d("id",Integer.toString(id));
        loadData(id);
    }


    public String setYear (String releasedDate) {
        Log.d("release",releasedDate);
        if (releasedDate.equals("tba"))
            return "TBA";
        else
            return releasedDate.substring(0,releasedDate.indexOf("-"));
    }

    private void loadData (int id) {
        final populateNovelDetails d = new populateNovelDetails(id);
        d.start();
        try {
            d.join();
        } catch (InterruptedException f) { f.printStackTrace(); }

        Thread a = new Thread() {
            public void run() {
                data = d.getData();
                pictures = new ArrayList<novelScreenShot>(Arrays.asList(d.getScreens()));
            }
        };
        a.start();
        try {
            a.join();
        } catch (InterruptedException f) { f.printStackTrace(); }

        loadImages();
        title.setText(data.getTitle() + " (" + setYear(data.getReleased()) + ")");
        votes.setText(Integer.toString(data.getVoteCount()));
        rating.setText(data.getRating());
        popularity.setText(Double.toString(data.getPopularity()) + "%");
        length.setText(data.getLength());
        description.setText(removeSourceBrackets(data.getDescription()));
        languages.setText(makeStringFromArray(data.getLanguages()));
        platforms.setText(makeStringFromArray(data.getPlatforms()));

        Picasso.with(getApplicationContext()).load(data.getImage()).fit().into(icon);
    }

    private String makeStringFromArray (String [] array) {
        StringBuilder returnString = new StringBuilder();
        for (int x = 0; x<array.length; x++) {
            returnString.append(array[x]);
            returnString.append(" ");
        }
        return returnString.toString();
    }

    public static String removeSourceBrackets (String s) {
        int openBraceCount = 0, closeBraceCount = 0, startSearchPosition=0, braceStartPosition=0,nextOpenBrace,nextClosedBrace;
        Boolean removed = false;

        while (s.contains("[")&&s.contains("]")) { //repeat while the string contains braces
            braceStartPosition = startSearchPosition = s.indexOf("[");
            openBraceCount++;

            while (!removed) {
                System.out.println("The search is starting at " + Integer.toString(startSearchPosition));
                nextOpenBrace = s.indexOf("[", startSearchPosition+1);
                nextClosedBrace = s.indexOf("]", startSearchPosition+1);
                System.out.println("The value of nextOpenBrace is " + Integer.toString(nextOpenBrace) + " while the value of nextClosedBrace is " + Integer.toString(nextClosedBrace));

                if (nextClosedBrace == -1) //Not the same number of open braces as closed braces
                    return "The passed string does not have the same number of opening braces as closing braces";
                else if (nextOpenBrace == -1) { //There are no more open braces in the string
                    closeBraceCount ++;
                    startSearchPosition = nextClosedBrace;
                    System.out.println("nextOpenBrace == -1");
                    System.out.println("The brace is at location" + Integer.toString(nextClosedBrace));
                }
                else if (nextOpenBrace < nextClosedBrace) {      //There are 2 open braces in a row
                    System.out.println("nextOpenBrace < nextClosedBrace");
                    System.out.println("Open brace at location" + Integer.toString(nextOpenBrace));
                    startSearchPosition = nextOpenBrace;
                    openBraceCount++;
                } else { //Next is a closed brace
                    closeBraceCount++;
                    startSearchPosition = nextClosedBrace;
                    System.out.println("nextOpenBrace > nextClosedBrace");
                    System.out.println("The brace is at location" + Integer.toString(nextClosedBrace));
                }
                System.out.println("The number of open braces is " + Integer.toString(openBraceCount) + " the number of close braces is " + Integer.toString(closeBraceCount));

                if (closeBraceCount == openBraceCount) {
                    System.out.println("Removing from index " + Integer.toString(startSearchPosition) + " to index " + Integer.toString(nextClosedBrace+1));
                    System.out.println("Max string index is " + Integer.toString(s.length()-1));

                    String removeMe = s.substring(braceStartPosition, nextClosedBrace+1);
                    System.out.println("Removing the string " + removeMe);
                    s = s.replace(removeMe, "");
                    removed = true;
                }
                System.out.println("FINISHED INNER ITERATION");
            }
            removed = false;
            openBraceCount = closeBraceCount = 0;
        }
        return s;
    }


    private void loadImages () {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //adapter.setData(pictures);
                //adapter.notifyDataSetChanged();
                imagePager.setAdapter(new imagePagerAdapter(getApplicationContext(),pictures));
                imagePager.setOffscreenPageLimit(2);

            }
        });
    }

}
