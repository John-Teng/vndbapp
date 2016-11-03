package ecez.vndbapp.controller;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.model.novelScreenShot;

/**
 * Created by Teng on 11/2/2016.
 */
public class imagePagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<novelScreenShot> pictures;

        public imagePagerAdapter(Context context, ArrayList<novelScreenShot> pictures) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.pictures = pictures;
        }

        @Override
        public int getCount() {
            return pictures.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String picture = pictures.get(position).getImage();
            View pictures = mLayoutInflater.inflate(R.layout.image_pager_layout, container, false);
            ImageView imageView = (ImageView) pictures.findViewById(R.id.screenshot);

            Picasso.with(mContext).load(picture).into(imageView);
            container.addView(pictures);

            return pictures;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
