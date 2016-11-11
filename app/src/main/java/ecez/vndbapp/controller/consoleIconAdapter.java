package ecez.vndbapp.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.model.console;
import ecez.vndbapp.model.country;

/**
 * Created by Teng on 10/10/2016.
 */
public class consoleIconAdapter extends RecyclerView.Adapter<consoleIconAdapter.holder>{

    private ArrayList<console> consoles = new ArrayList<console>();
    private LayoutInflater inflater;
    private Context context;

    public consoleIconAdapter(ArrayList<console> consoles, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.consoles = consoles;
        this.context = context;
    }
    public void setData (ArrayList<console> newData) {
        this.consoles = newData; //Adds additional data
    }

    @Override
    public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.country_icon, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        console console = consoles.get(position);

        switch (console.getConsoleName()) {
            case "en":
                holder.country.setText("English");
                holder.image.setImageResource(R.drawable.uk);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return consoles.size();
    }

    class holder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView country;
        public holder(View itemView) {

            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.flag);
            country = (TextView)itemView.findViewById(R.id.country);
        }
    }


}
