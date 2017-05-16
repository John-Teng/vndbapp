package ecez.vndbapp.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ecez.vndbapp.R;
import ecez.vndbapp.model.Release;
import ecez.vndbapp.model.ReleaseProducer;

public class ReleaseDetails extends AppCompatActivity {
    private Release mRelease;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_details);
        Intent intent = getIntent();
        mRelease = (Release) intent.getSerializableExtra("RELEASE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = (TextView)findViewById(R.id.toolbar_title);
        TextView originalTitle = (TextView)findViewById(R.id.release_details_original_title);
        TextView releaseDate = (TextView)findViewById(R.id.release_details_release_date);
        TextView ageRating = (TextView)findViewById(R.id.release_details_age_rating);
        TextView type = (TextView)findViewById(R.id.release_details_type);
        TextView producers = (TextView)findViewById(R.id.release_details_producers);
        TextView countries = (TextView)findViewById(R.id.release_details_countries_label);
        TextView consoles = (TextView)findViewById(R.id.release_details_consoles_label);

        title.setText(mRelease.getTitle());
        originalTitle.setText(mRelease.getOriginal());
        releaseDate.setText(mRelease.getReleased());
        ageRating.setText(mRelease.getAgeRating());
        type.setText(mRelease.getType());

        ReleaseProducer [] producerArray = mRelease.getProducers();
        for (int x = 0; x< producerArray.length; x++) {
            producers.append(producerArray[x].name);
            producers.append("\n");
        }

        String [] languages = mRelease.getLanguages();
        for (int y = 0; y< languages.length; y++){
            countries.append(languages[y]);
        }
        String [] platforms = mRelease.getPlatforms();
        for (int z = 0; z< platforms.length; z++){
            consoles.append(platforms[z]);
        }

    }
}
