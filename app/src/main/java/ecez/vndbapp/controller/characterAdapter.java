package ecez.vndbapp.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.model.character;
import ecez.vndbapp.model.listItem;
import ecez.vndbapp.view.novelDetails;
import ecez.vndbapp.view.tabFragment1;

/**
 * Created by Teng on 10/10/2016.
 */
public class characterAdapter extends RecyclerView.Adapter<characterAdapter.holder>{

    private ArrayList<character> characters;
    private LayoutInflater inflater;
    private Context context;

    public characterAdapter (ArrayList<character> characters, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.characters = characters;
        this.context = context;
    }

    @Override
    public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.character_list_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = tabFragment1.recyclerView.getChildLayoutPosition(view);
            }
        });
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        character item = characters.get(position);
        holder.nameText.setText(item.getName());
        Picasso.with(context).load(item.getImage()).fit().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    class holder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private ImageView image;

        public holder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.character_list_name);
            image = (ImageView)itemView.findViewById(R.id.character_list_image);
        }
    }


}
