package ecez.vndbapp.controller.Adapters;

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

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.CharacterProfile;
import ecez.vndbapp.model.Character;

/**
 * Created by Teng on 10/10/2016.
 */
public class CharacterRecyclerAdapter extends RecyclerView.Adapter<CharacterRecyclerAdapter.holder>{

    private ArrayList<Character> characters;
    private LayoutInflater inflater;
    private Context context;
    private RecyclerView recyclerViewReference;
    private int novelID;

    public CharacterRecyclerAdapter(ArrayList<Character> characters, Context context, RecyclerView view, int novelID) {
        this.inflater = LayoutInflater.from(context);
        this.characters = characters;
        this.context = context;
        this.recyclerViewReference = view;
        this.novelID = novelID;
    }

    @Override
    public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.list_character_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerViewReference.getChildLayoutPosition(view);
                Intent intent = new Intent(context, CharacterProfile.class);
                intent.putExtra("CHARACTER", characters.get(itemPosition));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        Character item = characters.get(position);
        holder.nameText.setText(item.getName());
        Log.d("role",item.getRole(novelID));
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
        return characters.size();
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
