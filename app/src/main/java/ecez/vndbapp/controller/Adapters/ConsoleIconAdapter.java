package ecez.vndbapp.controller.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import ecez.vndbapp.R;
import ecez.vndbapp.controller.Utils.DisplayUtils;

/**
 * Created by Teng on 10/10/2016.
 */
public class ConsoleIconAdapter extends BaseAdapter {

    private String [] consoles;
    private LayoutInflater inflater;
    private Context context;

    public ConsoleIconAdapter(String [] consoles, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.consoles = consoles;
        this.context = context;
    }
    public void setData (String [] newData) {
        this.consoles = newData; //Adds additional data
    }

    @Override
    public int getCount() {
        return consoles.length;
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

        if (view == null) {
            gridView = inflater.inflate(R.layout.icon_console, null);
            TextView consoleText = (TextView) gridView.findViewById(R.id.console_text);
            ImageView consolePicture = (ImageView) gridView.findViewById(R.id.console);

            switch (consoles[i]) {
                case "gba":
                    consolePicture.setImageResource(R.drawable.gba_logo);
                    consoleText.setText("");
                    break;
                case "nds":
                    consolePicture.setImageResource(R.drawable.nds_logo);
                    consoleText.setText("");
                    break;
                case "n3d":
                    consolePicture.setImageResource(R.drawable.n3ds_logo);
                    consoleText.setText("");
                    break;
                case "psp":
                    consolePicture.setImageResource(R.drawable.psp_logo);
                    consoleText.setText("");
                    break;
                case "ps2":
                    consolePicture.setImageResource(R.drawable.ps2_logo);
                    consoleText.setText("");
                    break;
                case "ps3":
                    consolePicture.setImageResource(R.drawable.ps3_logo);
                    consoleText.setText("");
                    break;
                case "ps4":
                    consolePicture.setImageResource(R.drawable.ps4_logo);
                    consoleText.setText("");
                    break;
                case "psv":
                    consolePicture.setImageResource(R.drawable.psvita_logo);
                    consoleText.setText("");
                    break;
                case "xb3":
                    consolePicture.setImageResource(R.drawable.xbox360_logo);
                    consoleText.setText("");
                    break;
                case "win":
                    consolePicture.setImageResource(R.drawable.windows_logo);
                    consolePicture.getLayoutParams().height = (int)DisplayUtils.DpToPx(20,context);
                    consoleText.setText("");
                    break;
                case "ios":
                    consolePicture.setImageResource(R.drawable.ios_logo);
                    consolePicture.getLayoutParams().height = (int)DisplayUtils.DpToPx(18,context);
                    consoleText.setText("");
                    break;
                case "wii":
                    consolePicture.setImageResource(R.drawable.wii_logo);
                    consolePicture.getLayoutParams().height = (int)DisplayUtils.DpToPx(18,context);
                    consoleText.setText("");
                    break;
                case "and":
                    consolePicture.setImageResource(R.drawable.android_logo);
                    consolePicture.getLayoutParams().height = (int)DisplayUtils.DpToPx(20,context);
                    consoleText.setText("");
                    break;
                case "lin":
                    consolePicture.setImageResource(R.drawable.linux_logo);
                    consolePicture.getLayoutParams().height = (int)DisplayUtils.DpToPx(20,context);
                    consoleText.setText("");
                    break;
                default:
                    consolePicture.setImageResource(R.drawable.game_controller_logo);
                    consoleText.setText(consoles[i].toUpperCase());
                    break;
            }
        } else {
            gridView = view;
        }
        return gridView;
    }

}
