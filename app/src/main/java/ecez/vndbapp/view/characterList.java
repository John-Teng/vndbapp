package ecez.vndbapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.recyclerAdapter;
import ecez.vndbapp.model.character;

/**
 * Created by Teng on 11/23/2016.
 */
public class characterList extends Fragment {
        public static RecyclerView recyclerView;
        private recyclerAdapter adapter;
        private ProgressBar progressBar;
        private ArrayList<character> characters = new ArrayList<character>();
        private View view;
        private int pageCount = 1;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.character_list_fragment, container, false);
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
            progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
            progressBar.setVisibility(view.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
            recyclerView.setLayoutManager(layoutManager);

            adapter = new recyclerAdapter(characters, this.getContext());
            recyclerView.setAdapter(adapter);

            return view;
        }


    }
