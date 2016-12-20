package ecez.vndbapp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
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
import java.util.Timer;
import java.util.TimerTask;

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
    TextView title, votes, rating, popularity, length, characterLabel1, characterLabel2, characterLabel3, characterRole1, characterRole2, characterRole3;
    Button expandButton, seeMoreCharacters, backButton;
    ExpandableTextView description;
    ImageView icon, characterIcon1, characterIcon2, characterIcon3;
    FixedViewPager imagePager;
    DetailsData data;
    int novelID, timerCount;
    ArrayList<NovelScreenShot> pictures = new ArrayList<>();
    ArrayList<Country> countries = new ArrayList<>();
    ArrayList<Console> consoles = new ArrayList<>();
    ArrayList<Character> characters = new ArrayList<>();
    View quickstats, bodyLayout;
    final int TIMER_TIME = 500;
    private boolean viewsAreLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        seeMoreCharacters = (Button) findViewById(R.id.see_all_characters_button);

        title = (TextView)findViewById(R.id.toolbar_title);
        title.setVisibility(View.INVISIBLE);
        //developer = (TextView) findViewById(R.id.toolbar_subtitle);
        votes = (TextView) findViewById(R.id.quickstats_votes);
        rating = (TextView)findViewById(R.id.quickstats_rating);
        popularity = (TextView)findViewById(R.id.quickstats_popularity);
        length = (TextView)findViewById(R.id.quickstats_length);
        description = (ExpandableTextView)findViewById(R.id.description);
        icon = (ImageView) findViewById(R.id.novel_icon);
        icon.setImageDrawable(NovelDetails.novelIcon);
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

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerCount += 1;
                if (viewsAreLoaded) {
                    timer.cancel();
                    timer.purge();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bodyLayout.setVisibility(View.VISIBLE);
                            quickstats.setVisibility(View.VISIBLE);
                            title.setVisibility(View.VISIBLE);
                        }
                    });
                }
                if (timerCount >= 6) {
                    timer.cancel();
                    timer.purge();
                    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                    alertDialog.setTitle("Loading Error");
                    alertDialog.setMessage("There was an error with loading the data, please try again!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            });
                    alertDialog.show();
                }
            }
        }, TIMER_TIME);
    }

    private void loadCharacterData (int id) {
        final PopulateCharacters p = new PopulateCharacters(id);
        p.start();
        try {
            p.join();
        } catch (InterruptedException f) {
            f.printStackTrace();
        }

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

        Thread b = new Thread() {
            public void run() {
                loadCountryIcons();
                loadConsoleIcons();
            }
        };
        b.start();
        try {
            b.join();
        } catch (InterruptedException f) { f.printStackTrace(); }
        loadNovelViews();
    }

    private void loadNovelViews() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                title.setText(data.getTitleWithDate());
                votes.setText(data.getVoteCount());
                rating.setText(data.getRating());
                popularity.setText(data.getPopularity());
                description.setText(data.getDescriptionWithoutBrackets());
                length.setText(data.getLength());
                imageAdapter.setImage(pictures);
                imageAdapter.notifyDataSetChanged();
                viewsAreLoaded = true;
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
}
