package ecez.vndbapp.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.characterAdapter;
import ecez.vndbapp.model.character;

public class characterList extends AppCompatActivity {
    public static RecyclerView recyclerView;
    private characterAdapter adapter;
    private ProgressBar progressBar;
    private View view;
    ArrayList<character> characters;
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
        characters = (ArrayList<character>) intent.getSerializableExtra("CHARACTERS");

        recyclerView = (RecyclerView)findViewById(R.id.character_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new characterAdapter (characters, getApplicationContext(), recyclerView, novelID);
        recyclerView.setAdapter(adapter);

    }

}

