package ecez.vndbapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        TextView originalNameLabel = (TextView) findViewById(R.id.character_original_name_label);
        TextView genderLabel = (TextView) findViewById(R.id.character_gender_label);
        TextView bloodTypeLabel = (TextView) findViewById(R.id.character_blood_type_label);
        TextView birthdayLabel = (TextView) findViewById(R.id.character_birthday_label);
        TextView otherNamesLabel = (TextView) findViewById(R.id.character_aliases_label);
        TextView heightLabel = (TextView) findViewById(R.id.character_height_label);
        TextView weightLabel = (TextView) findViewById(R.id.character_weight_label);
        TextView bustLabel = (TextView) findViewById(R.id.character_bust_label);
        TextView waistLabel = (TextView) findViewById(R.id.character_waist_label);
        TextView hipLabel = (TextView) findViewById(R.id.character_hip_label);

        Picasso
                .with(getApplicationContext())
                .load(character.getImage())
                .fit()
                .centerCrop()
                .into(picture);

        name.setText(character.getName());
        loadTextView(description, character.getDescription());
        loadTextView(originalName,originalNameLabel,character.getOriginal());
        loadTextView(gender,genderLabel,character.getGender());
        loadTextView(bloodType,bloodTypeLabel,character.getBloodt());
        loadTextView(birthday,birthdayLabel,character.getBirthday());
        loadTextView(otherNames,otherNamesLabel,character.getAliases());
        loadTextView(height,heightLabel,character.getHeight());
        loadTextView(weight,weightLabel,character.getWeight());
        loadTextView(bust,bustLabel,character.getBust());
        loadTextView(waist,waistLabel,character.getWaist());
        loadTextView(hip,hipLabel,character.getHip());

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                intent.putExtra("POSITION", 0);
                intent.putExtra("IMAGE_URLS", new ArrayList<NovelScreenShot>().add(new NovelScreenShot(character.getImage())));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    private void loadTextView (TextView field, String data) {
        if (data ==null) {
            field.setVisibility(View.GONE);
        } else {
            field.setText(data);
        }
    }

    private void loadTextView(TextView field, TextView label, Integer data) {
        if (data == null) {
            label.setVisibility(View.GONE);
            field.setVisibility(View.GONE);
        } else {
            field.setText(Integer.toString(data));
        }
    }

    private void loadTextView(TextView field, TextView label, String data) {
        if (data == null) {
            label.setVisibility(View.GONE);
            field.setVisibility(View.GONE);
        } else {
            field.setText(data);
        }
    }
}