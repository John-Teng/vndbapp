package ecez.vndbapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.imagePagerAdapter;
import ecez.vndbapp.model.fixedViewPager;
import ecez.vndbapp.model.novelScreenShot;

public class imageActivity extends AppCompatActivity {
    Button closeButton;
    int position;
    ArrayList<novelScreenShot> imageURLS;
    imagePagerAdapter imageAdapter;
    fixedViewPager imagePager;

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

        imagePager = (fixedViewPager) findViewById(R.id.fullscreen_imagePager);
        imageAdapter = new imagePagerAdapter(getApplicationContext(),imageURLS, this);
        imagePager.setAdapter(imageAdapter);
        imagePager.setOffscreenPageLimit(15);
        imagePager.setCurrentItem(position);

    }
}
