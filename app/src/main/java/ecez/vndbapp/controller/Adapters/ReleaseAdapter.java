package ecez.vndbapp.controller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.ReleaseDetails;
import ecez.vndbapp.model.Release;

/**
 * Created by johnteng on 2017-05-13.
 */

public class ReleaseAdapter extends RecyclerView.Adapter<ReleaseAdapter.holder>{

        private Release[] releases;
        private LayoutInflater inflater;
        private Context context;
        private RecyclerView recyclerViewReference;
        private int novelID;

        public ReleaseAdapter(Release[] releases, Context context, RecyclerView view, int novelID) {
            this.inflater = LayoutInflater.from(context);
            this.releases = releases;
            this.context = context;
            this.recyclerViewReference = view;
            this.novelID = novelID;
        }

    public void setData (Release [] releases) {
        this.releases = releases; //Adds additional data
    }

        @Override
        public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
            final View view = inflater.inflate(R.layout.release_list_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = recyclerViewReference.getChildLayoutPosition(view);
                    Intent intent = new Intent(context, ReleaseDetails.class);
                    intent.putExtra("RELEASES", releases[itemPosition]);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            return new holder(view);
        }

        @Override
        public void onBindViewHolder(holder holder, int position) {
            Release item = releases[position];

            holder.titleText.setText(item.getTitle());
            if (item.getPatch())
                holder.titleText.append(" (Patch)");
            holder.dateText.append(item.getReleased());
        }

        @Override
        public int getItemCount() {
            return releases.length;
        }

        class holder extends RecyclerView.ViewHolder {

            private TextView titleText, dateText;

            public holder(View itemView) {
                super(itemView);
                titleText = (TextView)itemView.findViewById(R.id.release_list_item_title);
                dateText = (TextView)itemView.findViewById(R.id.release_list_item_date);
            }
        }


}
