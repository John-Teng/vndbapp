package ecez.vndbapp.view;

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
import java.util.Arrays;

import at.blogc.android.views.ExpandableTextView;
import ecez.vndbapp.R;
import ecez.vndbapp.controller.ConsoleIconAdapter;
import ecez.vndbapp.controller.CountryIconAdapter;
import ecez.vndbapp.controller.ImagePagerAdapter;
import ecez.vndbapp.controller.PopulateCharacters;
import ecez.vndbapp.controller.PopulateNovelDetails;
import ecez.vndbapp.model.Character;
import ecez.vndbapp.model.Console;
import ecez.vndbapp.model.Country;
import ecez.vndbapp.model.DetailsData;
import ecez.vndbapp.model.FixedViewPager;
import ecez.vndbapp.model.NovelScreenShot;

public class NovelDetails extends AppCompatActivity {

    public static Drawable novelIcon;
    ConsoleIconAdapter consoleAdapter;
    CountryIconAdapter countryAdapter;
    LinearLayoutManager countryLayoutManager, consoleLayoutManager;
    ImagePagerAdapter imageAdapter;
    RecyclerView countryRecyclerView, consoleRecyclerView;
    Toolbar toolbar;
    TextView title, developer, votes, rating, popularity, length, characterLabel1, characterLabel2, characterLabel3, characterRole1, characterRole2, characterRole3;
    String descriptionText;
    Button expandButton, seeMoreCharacters;
    ExpandableTextView description;
    ImageView icon, characterIcon1, characterIcon2, characterIcon3;
    FixedViewPager imagePager;
    DetailsData data;
    int novelID;
    ArrayList<NovelScreenShot> pictures = new ArrayList<>();
    ArrayList<Country> countries = new ArrayList<>();
    ArrayList<Console> consoles = new ArrayList<>();
    ArrayList<Character> characters = new ArrayList<>();

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


        characterIcon1 = (ImageView) findViewById(R.id.character_image1);
        characterIcon2 = (ImageView) findViewById(R.id.character_image2);
        characterIcon3 = (ImageView) findViewById(R.id.character_image3);
        characterLabel1 = (TextView) findViewById(R.id.character_label1);
        characterLabel2 = (TextView) findViewById(R.id.character_label2);
        characterLabel3 = (TextView) findViewById(R.id.character_label3);
        characterRole1 = (TextView) findViewById(R.id.character_role1);
        characterRole2 = (TextView) findViewById(R.id.character_role2);
        characterRole3 = (TextView) findViewById(R.id.character_role3);
        seeMoreCharacters = (Button) findViewById(R.id.see_all_characters_button);

        title = (TextView)findViewById(R.id.appbar_title);
        developer = (TextView) findViewById(R.id.appbar_subtitle);
        votes = (TextView) findViewById(R.id.quickstats_votes);
        rating = (TextView)findViewById(R.id.quickstats_rating);
        popularity = (TextView)findViewById(R.id.quickstats_popularity);
        length = (TextView)findViewById(R.id.quickstats_length);
        description = (ExpandableTextView)findViewById(R.id.description);
        icon = (ImageView) findViewById(R.id.novel_icon);
        icon.setImageDrawable(NovelDetails.novelIcon);
        NovelDetails.novelIcon = null; //Set the icon back to null

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

        countryAdapter = new CountryIconAdapter(countries, getApplicationContext());
        countryRecyclerView.setLayoutManager(countryLayoutManager);
        countryRecyclerView.setAdapter(countryAdapter);

        consoleAdapter = new ConsoleIconAdapter(consoles, getApplicationContext());
        consoleRecyclerView.setLayoutManager(consoleLayoutManager);
        consoleRecyclerView.setAdapter(consoleAdapter);
        consoleRecyclerView.setItemViewCacheSize(20);

        imageAdapter = new ImagePagerAdapter(getApplicationContext(),pictures, imagePager, true);
        imagePager.setAdapter(imageAdapter);
        imagePager.setOffscreenPageLimit(15);

        this.novelID = Integer.parseInt(intent.getStringExtra("NOVEL_ID"));
        Log.d("id",Integer.toString(this.novelID));
        new Thread() {
            public void run() {
                loadNovelData(novelID);
                loadCharacterData(novelID);
            }
        }.start();
    }

    public String setYear (String releasedDate) { //Refactor
        Log.d("release",releasedDate);
        if (releasedDate.equals("tba"))
            return "TBA";
        else
            return releasedDate.substring(0,releasedDate.indexOf("-"));
    }


    private void loadCharacterData (int id) {
        final PopulateCharacters p = new PopulateCharacters(id);
        p.start();
        try {
            p.join();
        } catch (InterruptedException f) { f.printStackTrace(); }

        Thread a = new Thread() {
            public void run() {
                characters = p.getCharacters();
            }
        };
        a.start();
        try {
            a.join();
        } catch (InterruptedException f) { f.printStackTrace(); }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
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

    }

    private void loadNovelData (int id) {
        final PopulateNovelDetails d = new PopulateNovelDetails(id);
        d.start();
        try {
            d.join();
        } catch (InterruptedException f) { f.printStackTrace(); }

        Thread a = new Thread() {
            public void run() {
                data = d.getData();
                pictures = new ArrayList<>(Arrays.asList(d.getScreens()));
                pictures.add(new NovelScreenShot(data.getImage())); //Add the novel icon as the last image
            }
        };
        a.start();
        try {
            a.join();
        } catch (InterruptedException f) { f.printStackTrace(); }
        loadImages();
        loadViews();
        loadCountryIcons();
        loadConsoleIcons();
        loadDescription();
    }

    private void loadViews() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                title.setText(data.getTitle() + " (" + setYear(data.getReleased()) + ")"); //Refactor
                votes.setText(Integer.toString(data.getVoteCount())+ " votes");
                rating.setText(data.getRating());
                popularity.setText(Double.toString(data.getPopularity()) + "% popularity");
                length.setText(data.getLength());
            }
        });
    }

    private void loadCountryIcons () {
        String [] s = data.getLanguages();
        for (int x = 0; x<s.length; x++) {
            countries.add(new Country(s[x]));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                countryAdapter.setData(countries);
                countryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadConsoleIcons () {
        String [] s = data.getPlatforms();
        for (int x = 0; x<s.length; x++) {
            consoles.add(new Console(s[x]));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                consoleAdapter.setData(consoles);
                consoleAdapter.notifyDataSetChanged();
            }
        });
    }

    public static String removeSourceBrackets (String s) {
        int openBraceCount = 0, closeBraceCount = 0, startSearchPosition, braceStartPosition, nextOpenBrace, nextClosedBrace;
        Boolean removed = false;

        while (s.contains("[")&&s.contains("]")) { //repeat while the string contains braces
            braceStartPosition = startSearchPosition = s.indexOf("[");
            openBraceCount++;

            while (!removed) {
                nextOpenBrace = s.indexOf("[", startSearchPosition+1);
                nextClosedBrace = s.indexOf("]", startSearchPosition+1);

                if (nextClosedBrace == -1) //Not the same number of open braces as closed braces
                    return "The passed string does not have the same number of opening braces as closing braces";
                else if (nextOpenBrace == -1) { //There are no more open braces in the string
                    closeBraceCount ++;
                    startSearchPosition = nextClosedBrace;
                }
                else if (nextOpenBrace < nextClosedBrace) {      //There are 2 open braces in a row
                    startSearchPosition = nextOpenBrace;
                    openBraceCount++;
                } else { //Next is a closed brace
                    closeBraceCount++;
                    startSearchPosition = nextClosedBrace;
                }
                if (closeBraceCount == openBraceCount) {
                    String removeMe = s.substring(braceStartPosition, nextClosedBrace+1);
                    s = s.replace(removeMe, "");
                    removed = true;
                }
            }
            removed = false;
            openBraceCount = closeBraceCount = 0;
        }
        return s;
    }

    private void loadDescription () {
        Thread a = new Thread() {
            public void run() {
                descriptionText = removeSourceBrackets(data.getDescription());
            }
        };
        a.start();
        try {
            a.join();
        } catch (InterruptedException f) { f.printStackTrace(); }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                description.setText(descriptionText);

            }
        });
    }


    private void loadImages () {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageAdapter.setImage(pictures);
                imageAdapter.notifyDataSetChanged();
            }
        });
    }

}
