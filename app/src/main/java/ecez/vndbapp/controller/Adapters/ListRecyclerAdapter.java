package ecez.vndbapp.controller.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.NovelDetails;
import ecez.vndbapp.controller.NovelListFragment;
import ecez.vndbapp.model.Constants;
import ecez.vndbapp.model.NovelData;
import ecez.vndbapp.model.SystemStatus;

/**
 * Created by Teng on 10/10/2016.
 */
public class ListRecyclerAdapter extends VNRecyclerAdapter<ListRecyclerAdapter.holder>{

    public ListRecyclerAdapter(List<NovelData> listData, Context context) {
        super(listData, context);
    }
    public void setData (List<NovelData> newData) {
        this.listData = newData; //Adds additional data
    }

    @Override
    public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.list_card_item, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = NovelListFragment.recyclerView.getChildLayoutPosition(view);
                String id = listData.get(itemPosition).getId();

                ImageView image = (ImageView) view.findViewById(R.id.picture);
                NovelDetails.novelIcon = image.getDrawable();

                Intent intent = new Intent(context, NovelDetails.class);

//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation(activityReference, (View)image, "profile");
                intent.putExtra("NOVEL_ID", id);
                Log.d("New Activity","About to start the NovelDetails activity");
                context.startActivity(intent);
//                context.startActivity(intent, options.toBundle());
            }
        });
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        NovelData item = listData.get(position);
        holder.titleText.setText(item.getTitle());
        holder.ratingText.setText(item.getRating());
//        holder.lengthText.setText(item.getLength());
        if (item.isImage_nsfw() && SystemStatus.getInstance().blockNSFW)
            Picasso.with(context).load(Constants.NSFW_IMAGE).fit().into(holder.image);
        else
            Picasso.with(context).load(item.getImage()).fit().into(holder.image);
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
            image = (ImageView)itemView.findViewById(R.id.picture);
//            lengthText = (TextView)itemView.findViewById(R.id.length_text);
//            container = itemView.findViewById(R.id.card_layout);
        }
    }


}
