package ecez.vndbapp.controller;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Utils.DisplayUtils;
import ecez.vndbapp.model.Constants;
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
        TextView countries = (TextView)findViewById(R.id.release_details_country);
        TextView consoles = (TextView)findViewById(R.id.release_details_console);
        TextView notes = (TextView)findViewById(R.id.release_details_notes);
        ImageView countryImage = (ImageView)findViewById(R.id.release_details_country_image);
        ImageView consoleImage = (ImageView)findViewById(R.id.release_details_console_image);

        notes.setText(mRelease.getNotes());
        title.setText(mRelease.getTitle());
        if (mRelease.getPatch())
            title.append(" (Patch)");
        originalTitle.setText(mRelease.getOriginal());
        releaseDate.setText(mRelease.getDate());
        ageRating.setText(mRelease.getAgeRating());
        type.setText(mRelease.getType());

        ReleaseProducer [] producerArray = mRelease.getProducers();
        for (int x = 0; x< producerArray.length; x++) {
            producers.append(producerArray[x].name);
            producers.append("\n");
        }

        String [] languages = mRelease.getLanguages();
        loadCountryIcon(languages[0], countries, countryImage);
        Log.d("Num of Languages", Integer.toString(languages.length));
        String [] platforms = mRelease.getPlatforms();
        loadConsoleIcon(platforms[0], consoles, consoleImage);


        Button backButton = (Button)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void loadCountryIcon (String country, TextView text, ImageView image) {
        switch (country) {
            case "en":
                text.setText("English");
                image.setImageResource(R.drawable.uk);
                break;
            case "de":
                text.setText("German");
                image.setImageResource(R.drawable.germany);
                break;
            case "es":
                text.setText("Spanish");
                image.setImageResource(R.drawable.spain);
                break;
            case "it":
                text.setText("Italian");
                image.setImageResource(R.drawable.italy);
                break;
            case "ja":
                text.setText("Japanese");
                image.setImageResource(R.drawable.japan);
                break;
            case "ko":
                text.setText("Korean");
                image.setImageResource(R.drawable.korea);
                break;
            case "ru":
                text.setText("Russian");
                image.setImageResource(R.drawable.russia);
                break;
            case "zh":
                text.setText("Chinese");
                image.setImageResource(R.drawable.china);
                break;

            case "vi":
                text.setText("Vietnamese");
                image.setImageResource(R.drawable.vietnam);
                break;
            case "fr":
                text.setText("French");
                image.setImageResource(R.drawable.france);
                break;
            default:
                text.setText(country.toUpperCase());
                image.setImageResource(R.drawable.eu);
        }
    }

    private void loadConsoleIcon (String console, TextView text, ImageView image) {
        switch (console) {
            case "gba":
                image.setImageResource(R.drawable.gba_logo);
                text.setText("");
                break;
            case "nds":
                image.setImageResource(R.drawable.nds_logo);
                text.setText("");
                break;
            case "n3d":
                image.setImageResource(R.drawable.n3ds_logo);
                text.setText("");
                break;
            case "psp":
                image.setImageResource(R.drawable.psp_logo);
                text.setText("");
                break;
            case "ps2":
                image.setImageResource(R.drawable.ps2_logo);
                text.setText("");
                break;
            case "ps3":
                image.setImageResource(R.drawable.ps3_logo);
                text.setText("");
                break;
            case "ps4":
                image.setImageResource(R.drawable.ps4_logo);
                text.setText("");
                break;
            case "psv":
                image.setImageResource(R.drawable.psvita_logo);
                text.setText("");
                break;
            case "xb3":
                image.setImageResource(R.drawable.xbox360_logo);
                text.setText("");
                break;
            case "win":
                image.setImageResource(R.drawable.windows_logo);
                image.getLayoutParams().height = (int) DisplayUtils.DpToPx(20,getApplicationContext());
                text.setText("");
                break;
            case "ios":
                image.setImageResource(R.drawable.ios_logo);
                image.getLayoutParams().height = (int) DisplayUtils.DpToPx(18,getApplicationContext());
                text.setText("");
                break;
            case "wii":
                image.setImageResource(R.drawable.wii_logo);
                image.getLayoutParams().height = (int) DisplayUtils.DpToPx(18,getApplicationContext());
                text.setText("");
                break;
            case "and":
                image.setImageResource(R.drawable.android_logo);
                image.getLayoutParams().height = (int) DisplayUtils.DpToPx(20,getApplicationContext());
                text.setText("");
                break;
            case "lin":
                image.setImageResource(R.drawable.linux_logo);
                image.getLayoutParams().height = (int) DisplayUtils.DpToPx(20,getApplicationContext());
                text.setText("");
                break;
            default:
                image.setImageResource(R.drawable.game_controller_logo);
                text.setText(console.toUpperCase());
                break;
        }
    }


}
