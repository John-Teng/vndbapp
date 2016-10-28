package ecez.vndbapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.populateListItems;
import ecez.vndbapp.controller.populateNovelDetails;
import ecez.vndbapp.model.detailsData;
import ecez.vndbapp.model.listItem;

public class novelDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        detailsData data;

        int id = Integer.parseInt(intent.getStringExtra("NOVEL_ID"));
        setContentView(R.layout.activity_novel_details);
        Log.d("id",Integer.toString(id));

        populateNovelDetails d = new populateNovelDetails(id);
        d.start();
        try {
            d.join();
        } catch (InterruptedException f) { f.printStackTrace(); }
        data = d.getData();

    }
}
