package ecez.vndbapp.controller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
            final View view = inflater.inflate(R.layout.character_list_item, parent, false);
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
            holder.nameText.setText(item.getOriginal());
            holder.roleText.setText(item.getRole(novelID));
            Picasso
                    .with(context)
                    .load(item.getImage())
                    .fit()
                    .centerCrop()
                    .into(holder.image);
        }

        @Override
        public int getItemCount() {
            return characters.length;
        }

        class holder extends RecyclerView.ViewHolder {

            private TextView nameText, roleText;
            private ImageView image;

            public holder(View itemView) {
                super(itemView);
                nameText = (TextView)itemView.findViewById(R.id.character_list_name);
                image = (ImageView)itemView.findViewById(R.id.character_list_image);
                roleText = (TextView)itemView.findViewById(R.id.character_list_role);
            }
        }


    }


}
