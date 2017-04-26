package ecez.vndbapp.controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.ConsoleIconRecyclerAdapter;
import ecez.vndbapp.controller.Adapters.CountryIconRecyclerAdapter;
import ecez.vndbapp.controller.Adapters.ImagePagerAdapter;
import ecez.vndbapp.controller.Callbacks.NovelDetailsDataCallback;
import ecez.vndbapp.controller.NetworkRequests.PopulateCharacters;
import ecez.vndbapp.controller.NetworkRequests.PopulateNovelDetails;
import ecez.vndbapp.model.Character;
import ecez.vndbapp.model.Console;
import ecez.vndbapp.model.Country;
import ecez.vndbapp.model.DetailsData;
import ecez.vndbapp.model.Error;
import ecez.vndbapp.model.FixedViewPager;
import ecez.vndbapp.model.NovelScreenShot;

public class NovelDetails extends AppCompatActivity {

    public static Drawable novelIcon;
    private ConsoleIconRecyclerAdapter consoleAdapter;
    private CountryIconRecyclerAdapter countryAdapter;
    private LinearLayoutManager countryLayoutManager, consoleLayoutManager;
    private ImagePagerAdapter imageAdapter;
    private RecyclerView countryRecyclerView, consoleRecyclerView;
    private Toolbar toolbar;
    private TextView title, votes, rating, popularity, length, characterLabel1, characterLabel2, characterLabel3, characterRole1, characterRole2, characterRole3, genre;
    private Button expandButton, seeMoreCharacters, backButton;
    private ExpandableTextView description;
    private ImageView icon, characterIcon1, characterIcon2, characterIcon3;
    private FixedViewPager imagePager;
    private int novelID;

    private ArrayList<Character> characters = new ArrayList<>();
    private View quickstats, bodyLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("New Activity","NovelDetails activity has been started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_details);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.GRAY);

        toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        characterIcon1 = (ImageView) findViewById(R.id.character_image1);
        characterIcon2 = (ImageView) findViewById(R.id.character_image2);
        characterIcon3 = (ImageView) findViewById(R.id.character_image3);
        characterLabel1 = (TextView) findViewById(R.id.character_label1);
        characterLabel2 = (TextView) findViewById(R.id.character_label2);
        characterLabel3 = (TextView) findViewById(R.id.character_label3);
        characterRole1 = (TextView) findViewById(R.id.character_role1);
        characterRole2 = (TextView) findViewById(R.id.character_role2);
        characterRole3 = (TextView) findViewById(R.id.character_role3);
        genre = (TextView) findViewById(R.id.genres);
        seeMoreCharacters = (Button) findViewById(R.id.see_all_characters_button);

        title = (TextView)findViewById(R.id.toolbar_title);
//        title.setVisibility(View.INVISIBLE);
        votes = (TextView) findViewById(R.id.quickstats_votes);
        rating = (TextView)findViewById(R.id.quickstats_rating);
        popularity = (TextView)findViewById(R.id.quickstats_popularity);
        length = (TextView)findViewById(R.id.quickstats_length);
        description = (ExpandableTextView)findViewById(R.id.description);
        icon = (ImageView) findViewById(R.id.novel_icon);
//        icon.setImageDrawable(NovelDetails.novelIcon);
        NovelDetails.novelIcon = null; //Set the icon back to null

        quickstats = findViewById(R.id.quickstats); //References to the invisible layouts so that they can be set to visible when items are loaded
        bodyLayout = findViewById(R.id.details_content_layout);

        countryRecyclerView = (RecyclerView)findViewById(R.id.countries);
        consoleRecyclerView = (RecyclerView)findViewById(R.id.consoles);

        imagePager = (FixedViewPager) findViewById(R.id.imagePager);
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
        countryLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        consoleLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        countryAdapter = new CountryIconRecyclerAdapter(new ArrayList<Country>(), getApplicationContext());
        countryRecyclerView.setLayoutManager(countryLayoutManager);
        countryRecyclerView.setAdapter(countryAdapter);

        consoleAdapter = new ConsoleIconRecyclerAdapter(new ArrayList<Console>(), getApplicationContext());
        consoleRecyclerView.setLayoutManager(consoleLayoutManager);
        consoleRecyclerView.setAdapter(consoleAdapter);
        consoleRecyclerView.setItemViewCacheSize(20);

        imageAdapter = new ImagePagerAdapter(getApplicationContext(),new ArrayList<NovelScreenShot>(), true);
        imagePager.setAdapter(imageAdapter);
        imagePager.setOffscreenPageLimit(15);

        backButton = (Button)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        this.novelID = Integer.parseInt(intent.getStringExtra("NOVEL_ID"));
        Log.d("id",Integer.toString(this.novelID));
        new Thread() {
            public void run() {
                loadNovelData(novelID);
                loadCharacterData(novelID);
            }
        }.start();
    }

    @Override
    public void onBackPressed (){
        super.onBackPressed();
        finish();
    }

    private void loadCharacterData (int id) {
        Log.d("Calling Server","Requesting Character Details from the server");
        final PopulateCharacters p = new PopulateCharacters(id);
        p.start();
        try {
            p.join();
        } catch (InterruptedException f) {
            f.printStackTrace();
        }
        Log.d("Calling Server","Received Character Details from the server");

        Thread a = new Thread() {
            public void run() {
                characters = p.getCharacters();
            }
        };
        a.start();
        try {
            a.join();
        } catch (InterruptedException f) {
            f.printStackTrace();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (characters == null || characters.size() < 3) {
                    findViewById(R.id.character_panel_layout).setVisibility(View.GONE);
                    return;
                }
                Picasso
                        .with(getApplicationContext())
                        .load(characters.get(0).getImage())
                        .fit()
                        .centerCrop()
                        .into(characterIcon1);

                Picasso
                        .with(getApplicationContext())
                        .load(characters.get(1).getImage())
                        .fit()
                        .centerCrop()
                        .into(characterIcon2);
                Picasso
                        .with(getApplicationContext())
                        .load(characters.get(2).getImage())
                        .fit()
                        .centerCrop()
                        .into(characterIcon3);

                characterLabel1.setText(characters.get(0).getName());
                characterLabel2.setText(characters.get(1).getName());
                characterLabel3.setText(characters.get(2).getName());
                characterRole1.setText(characters.get(0).getRole(novelID));
                characterRole2.setText(characters.get(1).getRole(novelID));
                characterRole3.setText(characters.get(2).getRole(novelID));
            }
        });

        seeMoreCharacters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CharacterList.class);
                intent.putExtra("NOVEL_ID", novelID);
                intent.putExtra("CHARACTERS", characters);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        final View characterLabel1 = findViewById(R.id.character_layout1);
        final View characterLabel2 = findViewById(R.id.character_layout2);
        final View characterLabel3 = findViewById(R.id.character_layout3);

        View.OnClickListener clickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CharacterProfile.class);
                if (v==characterLabel1)
                    intent.putExtra("CHARACTER", characters.get(0));
                else if (v == characterLabel2)
                    intent.putExtra("CHARACTER", characters.get(1));
                else
                    intent.putExtra("CHARACTER", characters.get(2));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        };
        characterLabel1.setOnClickListener(clickHandler);
        characterLabel2.setOnClickListener(clickHandler);
        characterLabel3.setOnClickListener(clickHandler);
    }

    private void loadNovelData (int id) {
        Log.d("Calling Server","Requesting Novel Details from the server");
        final PopulateNovelDetails d = new PopulateNovelDetails(id);
        d.callback = new NovelDetailsDataCallback() {
            @Override
            public void onSuccessUI(List<Country> countryList, List<Console> consoleList, DetailsData detailsData,
                                    List<NovelScreenShot> novelScreenShots, String genres) {
                Picasso.with(getApplicationContext()).load(detailsData.getImage()).fit().into(icon);
                title.setText(detailsData.getTitleWithDate());
                votes.setText(detailsData.getVoteCount());
                rating.setText(detailsData.getRating());
                popularity.setText(detailsData.getPopularity());
                description.setText(detailsData.getDescriptionWithoutBrackets());
                length.setText(detailsData.getLength());
                genre.setText(genres);
                imageAdapter.setImage(novelScreenShots);
                imageAdapter.notifyDataSetChanged();
                countryAdapter.setData(countryList);
                countryAdapter.notifyDataSetChanged();
                consoleAdapter.setData(consoleList);
                consoleAdapter.notifyDataSetChanged();
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
        d.execute();
    }
}