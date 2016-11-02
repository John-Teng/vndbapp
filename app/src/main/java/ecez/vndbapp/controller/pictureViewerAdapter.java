package ecez.vndbapp.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.model.novelScreenShot;
import ecez.vndbapp.model.pictureViewerImage;

/**
 * Created by Teng on 10/10/2016.
 */
public class pictureViewerAdapter extends RecyclerView.Adapter<pictureViewerAdapter.holder>{

    private ArrayList<novelScreenShot> images = new ArrayList<novelScreenShot>();
    private LayoutInflater inflater;
    private Context context;

    public pictureViewerAdapter (ArrayList<novelScreenShot> images, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.images = images;
        this.context = context;
    }
    public void setData (ArrayList<novelScreenShot> newData) {
        this.images = newData; //Adds additional data
    }

    @Override
    public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.novel_details_image, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        novelScreenShot image = images.get(position);
        Picasso.with(context).load(image.getImage()).fit().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class holder extends RecyclerView.ViewHolder {

        private ImageView image;

        public holder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.novel_details_image);
        }
    }


}
