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
import ecez.vndbapp.model.listItem;
import ecez.vndbapp.view.novelDetails;
import ecez.vndbapp.view.tabFragment1;

/**
 * Created by Teng on 10/10/2016.
 */
public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.holder>{

    private ArrayList<listItem> listData;
    private LayoutInflater inflater;
    private Context context;

    public recyclerAdapter (ArrayList<listItem> listData, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.listData = listData;
        this.context = context;
    }
    public void setData (ArrayList<listItem> newData) {
        this.listData = newData; //Adds additional data
    }
    @Override
    public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.card_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = tabFragment1.recyclerView.getChildLayoutPosition(view);
                String id = listData.get(itemPosition).getId();

                final ImageView image = (ImageView) view.findViewById(R.id.picture);
                novelDetails.novelIcon = image.getDrawable();

                Log.d("id",id);
                Intent intent = new Intent(context, novelDetails.class);
                intent.putExtra("NOVEL_ID", id);
                context.startActivity(intent);
            }
        });
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        listItem item = listData.get(position);
        holder.titleText.setText("#"+item.getRank()+" - "+item.getTitle()+ " ("+setYear(item.getReleased())+")");
        holder.ratingText.setText(item.getRating());
        holder.lengthText.setText(item.getLength());
        Picasso.with(context).load(item.getImage()).fit().into(holder.image);
    }

    public String setYear (String releasedDate) {
        Log.d("release",releasedDate);
        if (releasedDate.equals("tba"))
            return "TBA";
        else
            return releasedDate.substring(0,releasedDate.indexOf("-"));
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    class holder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView ratingText;
        private TextView lengthText;
        private ImageView image;
        private View container;


        public holder(View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.card_item_title);
            ratingText = (TextView)itemView.findViewById(R.id.rating_text);
            lengthText = (TextView)itemView.findViewById(R.id.length_text);
            image = (ImageView)itemView.findViewById(R.id.picture);
            container = itemView.findViewById(R.id.card_layout);
        }
    }


}
