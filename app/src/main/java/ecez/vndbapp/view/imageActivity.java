package ecez.vndbapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.ImagePagerAdapter;
import ecez.vndbapp.model.FixedViewPager;
import ecez.vndbapp.model.NovelScreenShot;

public class ImageActivity extends AppCompatActivity {
    private Button closeButton;
    private int position;
    private ArrayList<NovelScreenShot> imageURLS;
    private ImagePagerAdapter imageAdapter;
    private FixedViewPager imagePager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        position = intent.getIntExtra("POSITION", 0);
        imageURLS = (ArrayList<NovelScreenShot>) intent.getSerializableExtra("IMAGE_URLS");

        closeButton = (Button)findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imagePager = (FixedViewPager) findViewById(R.id.fullscreen_imagePager);
        imageAdapter = new ImagePagerAdapter(getApplicationContext(),imageURLS, imagePager);
        imagePager.setAdapter(imageAdapter);
        imagePager.setOffscreenPageLimit(15);
        imagePager.setCurrentItem(position);

    }
}
