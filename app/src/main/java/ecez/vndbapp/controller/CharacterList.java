package ecez.vndbapp.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.CharacterRecyclerAdapter;
import ecez.vndbapp.model.Character;

public class CharacterList extends AppCompatActivity {
    public static RecyclerView recyclerView;
    private CharacterRecyclerAdapter adapter;
    private Character [] characters;
    private Intent intent;
    private int novelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        intent = getIntent();
        novelID = intent.getIntExtra("NOVEL_ID",-1);
        characters = (Character [])intent.getSerializableExtra("CHARACTERS");

        recyclerView = (RecyclerView)findViewById(R.id.character_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CharacterRecyclerAdapter(characters, getApplicationContext(), recyclerView, novelID);
        recyclerView.setAdapter(adapter);
    }

}

