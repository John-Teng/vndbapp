package ecez.vndbapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ecez.vndbapp.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class imageActivity extends AppCompatActivity {
    ImageView image;
    Button closeButton;
    PhotoViewAttacher attacher;
    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        Intent intent = getIntent();
        imageURL = intent.getStringExtra("IMAGE_URL");
        image = (ImageView)findViewById(R.id.fullscreen_image);

        closeButton = (Button)findViewById(R.id.close_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Picasso.with(getApplicationContext()).load(imageURL).into(image);
        attacher = new PhotoViewAttacher(image);


    }


}
