package ecez.vndbapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import ecez.vndbapp.R;
import ecez.vndbapp.model.Character;
import ecez.vndbapp.model.NovelScreenShot;
import ecez.vndbapp.model.Trait;

public class CharacterProfile extends AppCompatActivity {
    private Character character;
    private TextView name, originalName, gender, bloodType, birthday, otherNames,
            description, height, weight, bust, waist, hip;
    private ImageView picture;
    final int NUM_OF_BASIC_STATS = 5, NUM_OF_PHYSICAL_STATS = 5;
    private int [] numOfHiddenStats = {0,0};
    private HashMap<Integer,ArrayList<String>> traits = new HashMap<>();
    private TableLayout tableLayout;
    private Trait trait, parentTrait;

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
        loadTextView(originalName,originalNameLayout,character.getOriginal(), StatType.BASICSTAT);
        loadTextView(gender,genderLayout,character.getGender(),StatType.BASICSTAT);
        loadTextView(bloodType,bloodTypeLayout,character.getBloodt(),StatType.BASICSTAT);
        loadTextView(birthday,birthdayLayout,character.getBirthday(),StatType.BASICSTAT);
        loadTextView(otherNames,otherNamesLayout,character.getAliases(),StatType.BASICSTAT);
        loadTextView(height,heightLayout,character.getHeight(),StatType.PHYSICALSTAT);
        loadTextView(weight,weightLayout,character.getWeight(),StatType.PHYSICALSTAT);
        loadTextView(bust,bustLayout,character.getBust(),StatType.PHYSICALSTAT);
        loadTextView(waist,waistLayout,character.getWaist(),StatType.PHYSICALSTAT);
        loadTextView(hip,hipLayout,character.getHip(),StatType.PHYSICALSTAT);

        if (numOfHiddenStats[0] == NUM_OF_BASIC_STATS)
            findViewById(R.id.character_profile_basic_stats_label).setVisibility(View.GONE);
        if (numOfHiddenStats[1] == NUM_OF_PHYSICAL_STATS)
            findViewById(R.id.character_profile_physical_stats_label).setVisibility(View.GONE);

        traits.put(1,null);
        traits.put(35,null);
        traits.put(36,null);
        traits.put(37,null);
        traits.put(38,null);
        traits.put(39,null);
        traits.put(40,null);
        traits.put(41,null);
        traits.put(42,null);
        traits.put(43,null);
        traits.put(1625,null);
        loadTraits();
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
        Log.d("return trait","Returning the trait " + trait.getName() + " and has id " + trait.getId().toString());
        return trait;
    }

    private void loadTraits () {

        Log.d("Traits","Starting to load traits");
        String [] [] characterTraits = character.getTraits();

        for (int x = 0; x < characterTraits.length; x++){ //iterate for every character trait returned
            Log.d("Traits","Character Trait Array Index "+Integer.toString(x));
            int traitID = Integer.parseInt(characterTraits[x][0]);
            trait = vndatabaseapp.traitsMap.get(traitID);
            String name = trait.getName();


            Integer [] traitParents = trait.getParents();
            Log.d("Trait name","Trait name is "+name + " and it contains " + traitParents.length + " parent traits, and the first parent trait is " + traitParents[0]);
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
            Log.d("PARENT TRAIT","The Parent Trait is " + parentTrait.getName() + " and its id is " + parentTrait.getId().toString());
            if (parentTrait == null) {
                Log.d("parent trait","parent trait is null");
            }
            ArrayList<String> a;
            if (traits.get(parentTrait.getId())== null) {
                a = new ArrayList<>();
                Log.d("Parent","The parent " + parentTrait.getName() + " currently has no associated traits");
            } else {
                a = traits.get(parentTrait.getId());
                Log.d("Parent","The parent " + parentTrait.getName() + " has associated traits");
            }
            a.add(name);
            traits.put(parentTrait.getId(),a);

        }
        for (HashMap.Entry<Integer, ArrayList<String>> entry : traits.entrySet()) { //after all character traits have been added to the traits map, format the traits map to be displayed
            Log.d("traits hashmap","Iterating through traits hashmap");
            if (entry.getValue() == null)
                continue;

            StringBuilder body = new StringBuilder();

            for (String s: entry.getValue()) {
                Log.d("hashmap arraylist",s);
                body.append(s);
                body.append(", ");
            }
            String rowBody = body.toString().substring(0,body.toString().length()-2); //peel off the ", " at the end
            Log.d("traits hashmap","The list of body traits is : " + rowBody);
            Log.d("looking up","Looking up the trait " + entry.getKey().toString());
            String rowTitle = vndatabaseapp.traitsMap.get(entry.getKey()).getName();
            Log.d("Trait Info","Trait category is "+rowTitle+" and the Trait is "+rowBody);

            createTableRow(rowTitle, rowBody);
        }
    }

    private void createTableRow (String title, String body ) {
        Log.d("TableLayout","Adding new row for the "+body+" trait");
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


    private void loadTextView(TextView field, View Layout, String data, StatType stat) {
        if (data == null) {
            if (stat == StatType.BASICSTAT)
                numOfHiddenStats[0] ++;
            else if (stat == StatType.PHYSICALSTAT)
                numOfHiddenStats[1] ++;
            Layout.setVisibility(View.GONE);
            field.setVisibility(View.GONE);
        } else {
            field.setText(data);
        }
    }

}

enum StatType {
    BASICSTAT,
    PHYSICALSTAT,
    TRAIT
}