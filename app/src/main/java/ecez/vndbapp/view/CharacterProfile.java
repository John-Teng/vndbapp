package ecez.vndbapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import ecez.vndbapp.R;
import ecez.vndbapp.model.Character;
import ecez.vndbapp.model.Trait;

public class CharacterProfile extends AppCompatActivity {
    private Character character;
    private TextView name, originalName, gender, bloodType, birthday, otherNames,
            description, height, weight, bust, waist, hip, traitLabel, basicStatsLabel, physicalStatsLabel;
    private ImageView picture;
    private HashMap<Integer,ArrayList<String>> traits = new HashMap<>();
    private int [] traitCategoryList = {1,35,36,37,38,39,40,41,42,43,1625};
    private TableLayout tableLayout;
    private Trait trait, parentTrait;
    private Button backButton;

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

        traitLabel = (TextView)findViewById(R.id.character_profile_traits_label);

        tableLayout = (TableLayout) findViewById(R.id.character_traits_table);
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
        basicStatsLabel = (TextView) findViewById(R.id.character_profile_basic_stats_label);
        physicalStatsLabel = (TextView) findViewById(R.id.character_profile_physical_stats_label);

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

        loadStat(originalName,originalNameLayout,character.getOriginal(), basicStatsLabel);
        loadStat(gender,genderLayout,character.getGender(),basicStatsLabel);
        loadStat(bloodType,bloodTypeLayout,character.getBloodt(),basicStatsLabel);
        loadStat(birthday,birthdayLayout,character.getBirthday(),basicStatsLabel);
        loadStat(otherNames,otherNamesLayout,character.getAliases(),basicStatsLabel);

        loadStat(height,heightLayout,character.getHeight(),physicalStatsLabel);
        loadStat(weight,weightLayout,character.getWeight(),physicalStatsLabel);
        loadStat(bust,bustLayout,character.getBust(),physicalStatsLabel);
        loadStat(waist,waistLayout,character.getWaist(),physicalStatsLabel);
        loadStat(hip,hipLayout,character.getHip(),physicalStatsLabel);


        for (int z = 0; z<traitCategoryList.length; z++) {
            traits.put(traitCategoryList[z],null);
        }
        loadTraits();

        backButton = (Button)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

    private Trait findParent (Trait trait) {
        Integer [] traitParents = trait.getParents();

        if (traitParents.length > 0) { //make another recursive call
            Trait nextTrait = vndatabaseapp.traitsMap.get(traitParents[0]);
            return findParent(nextTrait);
        }
        return trait;
    }

    private void loadTraits () {
        String [] [] characterTraits = character.getTraits();

        for (int x = 0; x < characterTraits.length; x++){ //iterate for every character trait returned
            int traitID = Integer.parseInt(characterTraits[x][0]);
            trait = vndatabaseapp.traitsMap.get(traitID);
            String name = trait.getName();

            Thread b = new Thread() {
                public void run() {
                    parentTrait = findParent(trait);
                }
            };
            b.start();
            try {
                b.join();
            } catch (InterruptedException f) {
                f.printStackTrace();
            }
            ArrayList<String> a;
            if (traits.get(parentTrait.getId())== null) {
                a = new ArrayList<>();
            } else {
                a = traits.get(parentTrait.getId());
            }
            a.add(name);
            traits.put(parentTrait.getId(),a);

        }
        for (int x = 0; x<traitCategoryList.length; x++) {
            if (traits.get(traitCategoryList[x]) == null)
                continue;
            StringBuilder body = new StringBuilder();
            for (String s: traits.get(traitCategoryList[x])) {
                body.append(s);
                body.append(", ");
            }
            String rowBody = body.toString().substring(0,body.toString().length()-2); //peel off the ", " at the end
            String rowTitle = vndatabaseapp.traitsMap.get(traitCategoryList[x]).getName();

            createTableRow(rowTitle, rowBody);
        }
    }

    private void createTableRow (String title, String body ) {
        traitLabel.setVisibility(View.VISIBLE);
        TableRow row= new TableRow(this);
        row.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        row.setPadding(0,convertDpToPx(5),0,convertDpToPx(5));

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        TextView traitParent = new TextView(this);
        TextView associatedTraits = new TextView(this);

        traitParent.setPadding(convertDpToPx(10),0,0,0);

        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);

        traitParent.setLayoutParams(params);
        associatedTraits.setLayoutParams(params);

        traitParent.setText(title);
        traitParent.setTextColor(Color.BLACK);
        associatedTraits.setText(body);

        row.addView(traitParent);
        row.addView(associatedTraits);

        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, convertDpToPx(1)));
        v.setBackgroundColor(getResources().getColor(R.color.colorDividerGray));

        tableLayout.addView(row);
        tableLayout.addView(v);
    }

    private int convertDpToPx(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }

    private void loadStat (TextView field, View Layout, String data, View label) {
        if (data == null) {
            Layout.setVisibility(View.GONE);
            field.setVisibility(View.GONE);
        } else {
            field.setText(data);
            label.setVisibility(View.VISIBLE);
        }
    }
}
