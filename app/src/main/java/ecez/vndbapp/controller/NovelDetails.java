package ecez.vndbapp.controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import at.blogc.android.views.ExpandableTextView;
import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.ConsoleIconAdapter;
import ecez.vndbapp.controller.Adapters.CountryIconAdapter;
import ecez.vndbapp.controller.Adapters.ImagePagerAdapter;
import ecez.vndbapp.controller.Callbacks.DefaultCallback;
import ecez.vndbapp.controller.Callbacks.ListCallback;
import ecez.vndbapp.controller.Callbacks.NovelDetailsDataCallback;
import ecez.vndbapp.controller.NetworkRequests.PopulateCharacters;
import ecez.vndbapp.controller.NetworkRequests.PopulateNovelDetails;
import ecez.vndbapp.controller.NetworkRequests.PopulateRelease;
import ecez.vndbapp.model.Character;
import ecez.vndbapp.model.Constants;
import ecez.vndbapp.model.CustomGridView;
import ecez.vndbapp.model.DetailsData;
import ecez.vndbapp.model.Error;
import ecez.vndbapp.model.FixedViewPager;
import ecez.vndbapp.model.NovelAnime;
import ecez.vndbapp.model.NovelScreenShot;
import ecez.vndbapp.model.Release;
import ecez.vndbapp.model.ReleaseProducer;
import ecez.vndbapp.model.SystemStatus;

public class NovelDetails extends AppCompatActivity {

    public static Drawable novelIcon;
    private ConsoleIconAdapter consoleAdapter;
    private CountryIconAdapter countryAdapter;
    private LinearLayoutManager consoleLayoutManager;
    private ImagePagerAdapter imageAdapter;
    private CustomGridView countryGridView, consoleGridView;
    private TextView votes, rating, popularity, length, characterLabel1, characterLabel2, characterLabel3
            , characterRole1, characterRole2, characterRole3, genre, measuringTextview, countriesHeader,
            consolesHeader, screenshotsHeader;
    private Button expandButton, seeMoreCharacters, seeMoreReleases;
    private ExpandableTextView description;
    private ImageView icon, characterIcon1, characterIcon2, characterIcon3;
    private FixedViewPager imagePager;
    private int mNovelID;
    private String mNovelName;
    private Character [] characters;
    private View detailsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("New Activity","NovelDetails activity has been started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_details);

        characterIcon1 = (ImageView) findViewById(R.id.character_image1);
        characterIcon2 = (ImageView) findViewById(R.id.character_image2);
        characterIcon3 = (ImageView) findViewById(R.id.character_image3);
        characterLabel1 = (TextView) findViewById(R.id.character_label1);
        characterLabel2 = (TextView) findViewById(R.id.character_label2);
        characterLabel3 = (TextView) findViewById(R.id.character_label3);
        characterRole1 = (TextView) findViewById(R.id.character_role1);
        characterRole2 = (TextView) findViewById(R.id.character_role2);
        characterRole3 = (TextView) findViewById(R.id.character_role3);
        measuringTextview = (TextView) findViewById(R.id.measuring_textview);
        genre = (TextView) findViewById(R.id.genres);
        countriesHeader = (TextView) findViewById(R.id.countries_label);
        consolesHeader = (TextView) findViewById(R.id.consoles_label);
        screenshotsHeader = (TextView) findViewById(R.id.imagePager_label);

        seeMoreCharacters = (Button) findViewById(R.id.see_all_characters_button);
        seeMoreReleases = (Button) findViewById(R.id.release_button);
        seeMoreReleases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReleaseList.class);
                intent.putExtra(Constants.INTENT_ID, mNovelID);
                intent.putExtra(Constants.INTENT_NAME, mNovelName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
        detailsLayout = findViewById(R.id.details_content_layout);

        votes = (TextView) findViewById(R.id.quickstats_votes);
        rating = (TextView)findViewById(R.id.quickstats_rating);
        popularity = (TextView)findViewById(R.id.quickstats_popularity);
        length = (TextView)findViewById(R.id.quickstats_length);
        description = (ExpandableTextView)findViewById(R.id.description);
        icon = (ImageView) findViewById(R.id.novel_icon);
        NovelDetails.novelIcon = null; //Set the icon back to null

        countryGridView = (CustomGridView) findViewById(R.id.countries);
        consoleGridView = (CustomGridView)findViewById(R.id.consoles);
        countryGridView.setFocusable(false);
        consoleGridView.setFocusable(false);

        imagePager = (FixedViewPager) findViewById(R.id.imagePager);
        expandButton = (Button) this.findViewById(R.id.expand_button);
        description.setInterpolator(new OvershootInterpolator());
        detailsLayout.setVisibility(View.GONE);
        measuringTextview.setVisibility(View.INVISIBLE);
        seeMoreReleases.setVisibility(View.GONE);
        expandButton.setVisibility(View.GONE);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.toggle();
                expandButton.setText(description.isExpanded() ? "Read More" : "Read Less");
            }
        });

        Intent intent = getIntent();

        countryAdapter = new CountryIconAdapter(new String[0], getApplicationContext());
        countryGridView.setAdapter(countryAdapter);

        consoleAdapter = new ConsoleIconAdapter(new String[0], getApplicationContext());
        consoleGridView.setAdapter(consoleAdapter);

        imageAdapter = new ImagePagerAdapter(new NovelScreenShot[0], getApplicationContext(), true);
        imagePager.setAdapter(imageAdapter);
        imagePager.setOffscreenPageLimit(15);

        mNovelID = Integer.parseInt(intent.getStringExtra(Constants.INTENT_ID));
        mNovelName = intent.getStringExtra(Constants.INTENT_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mNovelName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadNovelData(mNovelID);
        loadCharacterData(mNovelID);

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

    private void loadCharacterData (int id) {
        Log.d("Calling Server","Requesting Character Details from the server");
        final PopulateCharacters p = new PopulateCharacters(id);
        p.callback = new ListCallback() {
            @Override
            public void returnList(Object [] array) {
                characters = (Character [])array; //Downcast to character array

                seeMoreCharacters.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CharacterList.class);
                        intent.putExtra(Constants.INTENT_NAME, mNovelName);
                        intent.putExtra(Constants.INTENT_ID, mNovelID);
                        intent.putExtra(Constants.INTENT_CHARACTERS, characters);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                });


                View.OnClickListener clickHandler = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CharacterProfile.class);
                        switch (v.getId()) {
                            case R.id.character_layout1:
                                intent.putExtra("CHARACTER", characters[0]);
                                break;
                            case R.id.character_layout2:
                                intent.putExtra("CHARACTER", characters[1]);
                                break;
                            case R.id.character_layout3:
                                intent.putExtra("CHARACTER", characters[2]);
                                break;
                            default:
                                intent.putExtra("CHARACTER", characters[3]);
                                break;
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                };

                findViewById(R.id.character_layout1).setOnClickListener(clickHandler);
                findViewById(R.id.character_layout2).setOnClickListener(clickHandler);
                findViewById(R.id.character_layout3).setOnClickListener(clickHandler);
            }

            @Override
            public void onSuccessUI() {
                if (characters == null || characters.length < 3) {
                    Log.d("Characters","Character arraylist is null");
                    findViewById(R.id.character_panel_layout).setVisibility(View.GONE);
                    return;
                }
                Picasso
                        .with(getApplicationContext())
                        .load(characters[0].getImage())
                        .fit()
                        .centerCrop()
                        .into(characterIcon1);

                Picasso
                        .with(getApplicationContext())
                        .load(characters[1].getImage())
                        .fit()
                        .centerCrop()
                        .into(characterIcon2);
                Picasso
                        .with(getApplicationContext())
                        .load(characters[2].getImage())
                        .fit()
                        .centerCrop()
                        .into(characterIcon3);

                characterLabel1.setText(characters[0].getName());
                characterLabel2.setText(characters[1].getName());
                characterLabel3.setText(characters[2].getName());
                characterRole1.setText(characters[0].getRole(mNovelID));
                characterRole2.setText(characters[1].getRole(mNovelID));
                characterRole3.setText(characters[2].getRole(mNovelID));
            }

            @Override
            public void onFailure(Error error, String errorMessage) {
                Log.d("AsyncTask FAILURE", errorMessage);
                if (error != null) {
                    Log.d("Error ID:", error.getId());
                    Log.d("Error Message:", error.getMsg());
                }
            }
        };
        p.execute();
    }

    private void loadNovelData (final int id) {
        Log.d("Calling Server","Requesting Novel Details from the server");
        final PopulateNovelDetails d = new PopulateNovelDetails(id);
        d.callback = new NovelDetailsDataCallback() {
            @Override
            public void onSuccessUI(final DetailsData detailsData, String genres) {
                if (detailsData.isImage_nsfw() && SystemStatus.getInstance().blockNSFW)
                    Picasso.with(getApplicationContext()).load(Constants.NSFW_IMAGE).fit().centerCrop().into(icon);
                else
                    Picasso.with(getApplicationContext()).load(detailsData.getImage()).fit().centerCrop().into(icon);
                measuringTextview.setText(detailsData.getDescriptionWithoutBrackets());

                if (measuringTextview.getLineCount() > 9) {
                    Log.d("Line Count", "NUM OF LINES IS GREATER THAN 8");
                    expandButton.setVisibility(View.VISIBLE);
                }
                measuringTextview.setVisibility(View.GONE);
                votes.setText(detailsData.getVoteCount());
                rating.setText(detailsData.getRating());
                popularity.setText(detailsData.getPopularity());
                description.setText(detailsData.getDescriptionWithoutBrackets());
                length.setText(detailsData.getLength());
                genre.setText(genres);
                TextView releaseDate = (TextView)findViewById(R.id.info_release_date);
                releaseDate.setText(detailsData.getDate());
                TextView aliases = (TextView)findViewById(R.id.info_aliases);
                aliases.setText(detailsData.getAliases());
                TextView originalTitle = (TextView) findViewById(R.id.info_original_title);
                originalTitle.setText(detailsData.getOriginal());
                NovelAnime [] animes = detailsData.getAnime();

                if (animes.length > 0) {
                    TextView anime = (TextView) findViewById(R.id.info_anime);
                    StringBuilder f = new StringBuilder();
                    for (int z = 0; z < animes.length; z++) {
                        f.append(animes[z].title_romaji + ",\n");
                    }
                    if (f.equals("")) {
                        anime.setText("None");
                    } else {
                        anime.setText(f.substring(0,f.length()-2));
                    }
                }

                PopulateRelease p = new PopulateRelease(id, "basic,details,producers",detailsData.getReleased(), new DefaultCallback<Release[]>() {
                    @Override
                    public void onSuccess(Release [] releases) {
                        seeMoreReleases.setVisibility(View.VISIBLE);
                        Release release = releases[0];
                        TextView developer = (TextView)findViewById(R.id.info_developer);
                        ReleaseProducer [] producers = release.getProducers();
                        TextView publisher = (TextView)findViewById(R.id.info_publisher);
                        TextView age = (TextView)findViewById(R.id.info_age_rating);
                        age.setText(release.getAgeRating());

                        if (producers.length > 0) {
                            StringBuilder a = new StringBuilder();
                            StringBuilder b = new StringBuilder();

                            for (int x = 0; x < producers.length; x++) {
                                if (producers[x].developer == true)
                                    a.append(producers[x].name + ", ");
                                if (producers[x].publisher == true)
                                    b.append(producers[x].name + ", ");
                            }
                            if (a.toString().equals(""))
                                developer.setText("Not Available");
                            else
                                developer.setText(a.substring(0, a.length() - 2));
                            if (b.toString().equals(""))
                                publisher.setText("Not Available");
                            else
                                publisher.setText(b.substring(0, b.length() - 2));
                        } else {
                            developer.setText("Not Available");
                            publisher.setText("Not Available");
                        }
                    }

                    @Override
                    public void onFailure(Error error, String errorMessage) {
                        Log.d("AsyncTask FAILURE",errorMessage);
                        if (error!=null){
                            Log.d("Error ID:",error.getId());
                            Log.d("Error Message:",error.getMsg());
                        }
                    }
                });
                p.execute();

                if (detailsData.getScreens().length == 0) {
                    imagePager.setVisibility(View.GONE);
                    screenshotsHeader.setText("No screenshots available");
                } else {
                    imageAdapter.setImage(detailsData.getScreens());
                    imageAdapter.notifyDataSetChanged();
                }
                if (detailsData.getLanguages().length == 0) {
                    countryGridView.setVisibility(View.GONE);
                    countriesHeader.setText("No language information available");
                } else {
                    countryAdapter.setData(detailsData.getLanguages());
                    countryAdapter.notifyDataSetChanged();
                }
                if (detailsData.getPlatforms().length == 0) {
                    consoleGridView.setVisibility(View.GONE);
                    consolesHeader.setText("No platform information available");
                } else {
                    consoleAdapter.setData(detailsData.getPlatforms());
                    consoleAdapter.notifyDataSetChanged();
                }
                detailsLayout.setVisibility(View.VISIBLE);
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
