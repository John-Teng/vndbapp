package ecez.vndbapp.controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.Adapters.CharacterRecyclerAdapter;
import ecez.vndbapp.model.Character;

public class CharacterList extends AppCompatActivity {
    public static RecyclerView recyclerView;
    private CharacterRecyclerAdapter adapter;
    private ArrayList<Character> characters;
    private Intent intent;
    private int novelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.GRAY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        intent = getIntent();
        novelID = intent.getIntExtra("NOVEL_ID",-1);
        characters = (ArrayList<Character>) intent.getSerializableExtra("CHARACTERS");

        recyclerView = (RecyclerView)findViewById(R.id.character_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CharacterRecyclerAdapter(characters, getApplicationContext(), recyclerView, novelID);
        recyclerView.setAdapter(adapter);
    }

}

