package ecez.vndbapp.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.CharacterRecyclerAdapter;
import ecez.vndbapp.model.Character;
import ecez.vndbapp.model.Constants;

public class CharacterList extends AppCompatActivity {
    public static RecyclerView recyclerView;
    private CharacterRecyclerAdapter adapter;
    private Character [] characters;
    private Intent intent;
    private int mNovelID;
    private String mNovelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        intent = getIntent();
        mNovelID = intent.getIntExtra(Constants.INTENT_ID,-1);
        mNovelName = intent.getStringExtra(Constants.INTENT_NAME);
        characters = (Character [])intent.getSerializableExtra(Constants.INTENT_CHARACTERS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Characters");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.character_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CharacterRecyclerAdapter(characters, getApplicationContext(), recyclerView, mNovelID);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

