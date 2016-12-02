package ecez.vndbapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.model.Character;
import ecez.vndbapp.model.NovelScreenShot;

public class CharacterProfile extends AppCompatActivity {
    private Character character;
    private TextView name, originalName, gender, bloodType, birthday, otherNames,
            description, height, weight, bust, waist, hip;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_profile);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.GRAY);

        Intent intent = getIntent();
        character = (Character) intent.getSerializableExtra("CHARACTER");

        name = (TextView) findViewById(R.id.toolbar_title);
        originalName = (TextView) findViewById(R.id.character_original_name);
        gender = (TextView) findViewById(R.id.character_gender);
        bloodType = (TextView) findViewById(R.id.character_blood_type);
        birthday = (TextView) findViewById(R.id.character_birthday);
        otherNames = (TextView) findViewById(R.id.character_aliases);
        description = (TextView) findViewById(R.id.character_description);
        height = (TextView) findViewById(R.id.character_height);
        weight = (TextView) findViewById(R.id.character_weight);
        bust = (TextView) findViewById(R.id.character_bust);
        waist = (TextView) findViewById(R.id.character_waist);
        hip = (TextView) findViewById(R.id.character_hip);
        picture = (ImageView) findViewById(R.id.character_profile_image);

        View originalNameLayout = findViewById(R.id.character_original_name_layout);
        View genderLayout = findViewById(R.id.character_gender_layout);
        View bloodTypeLayout =  findViewById(R.id.character_blood_type_layout);
        View birthdayLayout =  findViewById(R.id.character_birthday_layout);
        View otherNamesLayout =  findViewById(R.id.character_aliases_layout);
        View heightLayout = findViewById(R.id.character_height_layout);
        View weightLayout =  findViewById(R.id.character_weight_layout);
        View bustLayout = findViewById(R.id.character_bust_layout);
        View waistLayout =  findViewById(R.id.character_waist_layout);
        View hipLayout = findViewById(R.id.character_hip_layout);

        Picasso
                .with(getApplicationContext())
                .load(character.getImage())
                .fit()
                .centerCrop()
                .into(picture);

        name.setText(character.getName());
        loadTextView(description, character.getDescription());
        loadTextView(originalName,originalNameLayout,character.getOriginal());
        loadTextView(gender,genderLayout,character.getGender());
        loadTextView(bloodType,bloodTypeLayout,character.getBloodt());
        loadTextView(birthday,birthdayLayout,character.getBirthday());
        loadTextView(otherNames,otherNamesLayout,character.getAliases());
        loadTextView(height,heightLayout,character.getHeight());
        loadTextView(weight,weightLayout,character.getWeight());
        loadTextView(bust,bustLayout,character.getBust());
        loadTextView(waist,waistLayout,character.getWaist());
        loadTextView(hip,hipLayout,character.getHip());

    }

    private void loadTextView (TextView field, String data) {
        if (data ==null) {
            field.setVisibility(View.GONE);
        } else {
            field.setText(data);
        }
    }

    private void loadTextView(TextView field, View layout, Integer data) {
        if (data == null) {
            layout.setVisibility(View.GONE);
            field.setVisibility(View.GONE);
        } else {
            field.setText(Integer.toString(data));
        }
    }

    private void loadTextView(TextView field, View Layout, String data) {
        if (data == null) {
            Layout.setVisibility(View.GONE);
            field.setVisibility(View.GONE);
        } else {
            field.setText(data);
        }
    }
}