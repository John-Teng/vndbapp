package ecez.vndbapp.controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ecez.vndbapp.R;

/**
 * Created by Teng on 10/10/2016.
 */
public class CountryIconAdapter extends BaseAdapter {

    private String [] countries;
    private LayoutInflater inflater;

    public CountryIconAdapter(String [] countries, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.countries = countries;
    }
    public void setData (String [] newData) {
        this.countries = newData;
    }

    @Override
    public int getCount() {
        return countries.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View gridView;

        if (view == null){
            gridView = inflater.inflate(R.layout.icon_country, null);
            TextView text = (TextView) gridView.findViewById(R.id.country);
            ImageView image = (ImageView) gridView.findViewById(R.id.flag);

            switch (countries[i]) {
                case "en":
                    text.setText("English");
                    image.setImageResource(R.drawable.uk);
                    break;
                case "de":
                    text.setText("German");
                    image.setImageResource(R.drawable.germany);
                    break;
                case "es":
                    text.setText("Spanish");
                    image.setImageResource(R.drawable.spain);
                    break;
                case "it":
                    text.setText("Italian");
                    image.setImageResource(R.drawable.italy);
                    break;
                case "ja":
                    text.setText("Japanese");
                    image.setImageResource(R.drawable.japan);
                    break;
                case "ko":
                    text.setText("Korean");
                    image.setImageResource(R.drawable.korea);
                    break;
                case "ru":
                    text.setText("Russian");
                    image.setImageResource(R.drawable.russia);
                    break;
                case "zh":
                    text.setText("Chinese");
                    image.setImageResource(R.drawable.china);
                    break;

                case "vi":
                    text.setText("Vietnamese");
                    image.setImageResource(R.drawable.vietnam);
                    break;
                case "fr":
                    text.setText("French");
                    image.setImageResource(R.drawable.france);
                    break;
                default:
                    text.setText(countries[i].toUpperCase());
                    image.setImageResource(R.drawable.eu);
            }

        } else {
            gridView = view;
        }
        return gridView;
    }


}
