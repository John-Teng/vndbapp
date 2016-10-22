package ecez.vndbapp.controller;

import android.content.Context;
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
        this.listData = newData;
    }
    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        listItem item = listData.get(position);
        holder.titleText.setText("#"+item.getRank()+" - "+item.getTitle());
        Log.d("Setting","Set title text");
        holder.ratingText.setText(item.getRating());
        Log.d("Setting","Set rating text");
        holder.lengthText.setText(item.getLength());
        Log.d("Setting","Set length text");
        Picasso.with(context).load(item.getImage()).fit().into(holder.image);
        Log.d("Setting","Set image destination to " + item.getImage());
        //holder.image.setImageResource(item.getImageResourceId());
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

            titleText = (TextView)itemView.findViewById(R.id.title);
            ratingText = (TextView)itemView.findViewById(R.id.rating_text);
            lengthText = (TextView)itemView.findViewById(R.id.length_text);
            image = (ImageView)itemView.findViewById(R.id.picture);
            container = itemView.findViewById(R.id.card_layout);
        }
    }


}
