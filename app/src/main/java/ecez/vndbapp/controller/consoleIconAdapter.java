package ecez.vndbapp.controller;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.model.Console;

/**
 * Created by Teng on 10/10/2016.
 */
public class ConsoleIconAdapter extends RecyclerView.Adapter<ConsoleIconAdapter.holder>{

    private ArrayList<Console> consoles = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public ConsoleIconAdapter(ArrayList<Console> consoles, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.consoles = consoles;
        this.context = context;
    }
    public void setData (ArrayList<Console> newData) {
        this.consoles = newData; //Adds additional data
    }
    public float convertDpToPixel(float dp){
        Resources resources = this.context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    public holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.icon_console, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        Console console = consoles.get(position);

        switch (console.getConsoleName()) {
            case "gba":
                holder.consolePicture.setImageResource(R.drawable.gba_logo);
                holder.consoleText.setText("");
                break;
            case "nds":
                holder.consolePicture.setImageResource(R.drawable.nds_logo);
                holder.consoleText.setText("");
                break;
            case "n3d":
                holder.consolePicture.setImageResource(R.drawable.n3ds_logo);
                holder.consoleText.setText("");
                break;
            case "psp":
                holder.consolePicture.setImageResource(R.drawable.psp_logo);
                holder.consoleText.setText("");
                break;
            case "ps2":
                holder.consolePicture.setImageResource(R.drawable.ps2_logo);
                holder.consoleText.setText("");
                break;
            case "ps3":
                holder.consolePicture.setImageResource(R.drawable.ps3_logo);
                holder.consoleText.setText("");
                break;
            case "ps4":
                holder.consolePicture.setImageResource(R.drawable.ps4_logo);
                holder.consoleText.setText("");
                break;
            case "psv":
                holder.consolePicture.setImageResource(R.drawable.psvita_logo);
                holder.consoleText.setText("");
                break;
            case "xb3":
                holder.consolePicture.setImageResource(R.drawable.xbox360_logo);
                holder.consoleText.setText("");
                break;
            case "win":
                holder.consolePicture.setImageResource(R.drawable.windows_logo);
                holder.consolePicture.getLayoutParams().height = (int)convertDpToPixel(20);
                holder.consoleText.setText("");
                break;
            case "ios":
                holder.consolePicture.setImageResource(R.drawable.ios_logo);
                holder.consolePicture.getLayoutParams().height = (int)convertDpToPixel(18);
                holder.consoleText.setText("");
                break;
            case "wii":
                holder.consolePicture.setImageResource(R.drawable.wii_logo);
                holder.consolePicture.getLayoutParams().height = (int)convertDpToPixel(18);
                holder.consoleText.setText("");
                break;
            case "and":
                holder.consolePicture.setImageResource(R.drawable.android_logo);
                holder.consolePicture.getLayoutParams().height = (int)convertDpToPixel(20);
                holder.consoleText.setText("");
                break;
            case "lin":
                holder.consolePicture.setImageResource(R.drawable.linux_logo);
                holder.consolePicture.getLayoutParams().height = (int)convertDpToPixel(20);
                holder.consoleText.setText("");
                break;
            default:
                holder.consolePicture.setImageResource(R.drawable.game_controller_logo);
                holder.consoleText.setText(console.getConsoleName().toUpperCase());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return consoles.size();
    }

    class holder extends RecyclerView.ViewHolder {

        private ImageView consolePicture;
        private TextView consoleText;
        public holder(View itemView) {

            super(itemView);
            consolePicture = (ImageView)itemView.findViewById(R.id.console);
            consoleText = (TextView)itemView.findViewById(R.id.console_text);
        }
    }


}
