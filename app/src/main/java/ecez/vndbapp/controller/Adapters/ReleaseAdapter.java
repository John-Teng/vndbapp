package ecez.vndbapp.controller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.ReleaseDetails;
import ecez.vndbapp.model.Constants;
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
                    intent.putExtra("RELEASE", releases[itemPosition]);
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
            holder.dateText.setText("Released:   " + item.getDate());
            loadConsoleIcon(item.getPlatforms()[0], holder.platformText, holder.platformIcon);
            loadCountryIcon(item.getLanguages()[0], holder.languageText, holder.countryIcon);
        }

        @Override
        public int getItemCount() {
            return releases.length;
        }

    private void loadCountryIcon (String country, TextView text, ImageView image) {
        switch (country) {
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
                text.setText(country.toUpperCase());
                image.setImageResource(R.drawable.eu);
        }
    }

    private void loadConsoleIcon (String console, TextView text, ImageView image) {
        switch (console) {
            case "gba":
                image.setImageResource(R.drawable.gba_logo);
                text.setText("");
                break;
            case "nds":
                image.setImageResource(R.drawable.nds_logo);
                text.setText("");
                break;
            case "n3d":
                image.setImageResource(R.drawable.n3ds_logo);
                text.setText("");
                break;
            case "psp":
                image.setImageResource(R.drawable.psp_logo);
                text.setText("");
                break;
            case "ps2":
                image.setImageResource(R.drawable.ps2_logo);
                text.setText("");
                break;
            case "ps3":
                image.setImageResource(R.drawable.ps3_logo);
                text.setText("");
                break;
            case "ps4":
                image.setImageResource(R.drawable.ps4_logo);
                text.setText("");
                break;
            case "psv":
                image.setImageResource(R.drawable.psvita_logo);
                text.setText("");
                break;
            case "xb3":
                image.setImageResource(R.drawable.xbox360_logo);
                text.setText("");
                break;
            case "win":
                image.setImageResource(R.drawable.windows_logo);
                image.getLayoutParams().height = (int) convertDpToPixel(20);
                text.setText("");
                break;
            case "ios":
                image.setImageResource(R.drawable.ios_logo);
                image.getLayoutParams().height = (int) convertDpToPixel(18);
                text.setText("");
                break;
            case "wii":
                image.setImageResource(R.drawable.wii_logo);
                image.getLayoutParams().height = (int) convertDpToPixel(18);
                text.setText("");
                break;
            case "and":
                image.setImageResource(R.drawable.android_logo);
                image.getLayoutParams().height = (int) convertDpToPixel(20);
                text.setText("");
                break;
            case "lin":
                image.setImageResource(R.drawable.linux_logo);
                image.getLayoutParams().height = (int) convertDpToPixel(20);
                text.setText("");
                break;
            default:
                image.setImageResource(R.drawable.game_controller_logo);
                text.setText(console.toUpperCase());
                break;
        }
    }

    public float convertDpToPixel(float dp){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    class holder extends RecyclerView.ViewHolder {

        private TextView titleText, dateText, languageText, platformText;
        private ImageView countryIcon, platformIcon;

        public holder(View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.release_list_item_title);
            dateText = (TextView)itemView.findViewById(R.id.release_list_item_date);
            languageText = (TextView)itemView.findViewById(R.id.release_list_language);
            platformText = (TextView)itemView.findViewById(R.id.release_list_console);
            countryIcon = (ImageView)itemView.findViewById(R.id.release_list_country_icon);
            platformIcon = (ImageView)itemView.findViewById(R.id.release_list_console_icon);

        }
    }


}
