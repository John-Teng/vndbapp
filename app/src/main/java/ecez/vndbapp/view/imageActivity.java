package ecez.vndbapp.view;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.fullscreenImagePagerAdapter;
import ecez.vndbapp.model.novelScreenShot;

public class imageActivity extends AppCompatActivity {
    ImageView image;
    Button closeButton;
    int position;
    ArrayList<novelScreenShot> imageURLS;
    fullscreenImagePagerAdapter imageAdapter;
    ViewPager imagePager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        position = intent.getIntExtra("POSITION",0);
        imageURLS = (ArrayList<novelScreenShot>) intent.getSerializableExtra("IMAGE_URLS");

        closeButton = (Button)findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imagePager = (ViewPager) findViewById(R.id.fullscreen_imagePager);
        imageAdapter = new fullscreenImagePagerAdapter(getApplicationContext(),imageURLS);
        imagePager.setAdapter(imageAdapter);
        imagePager.setOffscreenPageLimit(9);
        imagePager.setCurrentItem(position);

    }


}
