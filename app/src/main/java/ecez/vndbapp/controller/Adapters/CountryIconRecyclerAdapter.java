package ecez.vndbapp.controller.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ecez.vndbapp.R;
import ecez.vndbapp.model.Country;

/**
 * Created by Teng on 10/10/2016.
 */
public class CountryIconRecyclerAdapter extends RecyclerView.Adapter<CountryIconRecyclerAdapter.holder>{

    private List<Country> countries = new ArrayList<>();
    private LayoutInflater inflater;

    public CountryIconRecyclerAdapter(List<Country> countries, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.countries = countries;
    }
    public void setData (List<Country> newData) {
        this.countries = newData; //Adds additional data
    }

    @Override
    public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.icon_country, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        Country country = countries.get(position);

        switch (country.getCountry()) {
            case "en":
                holder.country.setText("English");
                holder.image.setImageResource(R.drawable.uk);
                break;
            case "de":
                holder.country.setText("German");
                holder.image.setImageResource(R.drawable.germany);
                break;
            case "es":
                holder.country.setText("Spanish");
                holder.image.setImageResource(R.drawable.spain);
                break;
            case "it":
                holder.country.setText("Italian");
                holder.image.setImageResource(R.drawable.italy);
                break;
            case "ja":
                holder.country.setText("Japanese");
                holder.image.setImageResource(R.drawable.japan);
                break;
            case "ko":
                holder.country.setText("Korean");
                holder.image.setImageResource(R.drawable.korea);
                break;
            case "ru":
                holder.country.setText("Russian");
                holder.image.setImageResource(R.drawable.russia);
                break;
            case "zh":
                holder.country.setText("Chinese");
                holder.image.setImageResource(R.drawable.china);
                break;

            case "vi":
                holder.country.setText("Vietnamese");
                holder.image.setImageResource(R.drawable.vietnam);
                break;
            case "fr":
                holder.country.setText("French");
                holder.image.setImageResource(R.drawable.france);
                break;
            default:
                holder.country.setText(country.getCountry().toUpperCase());
                holder.image.setImageResource(R.drawable.eu);
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();
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
