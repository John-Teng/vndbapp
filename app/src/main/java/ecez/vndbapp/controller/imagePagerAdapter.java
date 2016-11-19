package ecez.vndbapp.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ecez.vndbapp.R;
import ecez.vndbapp.model.novelScreenShot;
import ecez.vndbapp.view.imageActivity;
import ecez.vndbapp.view.novelDetails;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Teng on 11/2/2016.
 */
public class imagePagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<novelScreenShot> pictures;
        Activity activityReference;
        Boolean shouldStartActivity = false;

        public imagePagerAdapter(Context context, ArrayList<novelScreenShot> pictures, Activity activity) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.pictures = pictures;
            this.activityReference = activity;
            if (activityReference.getLocalClassName().equals("view.novelDetails")) {
                this.shouldStartActivity = true;
            }
        }

    public void setImage (ArrayList<novelScreenShot> pictures) {
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
        public Object instantiateItem(ViewGroup container, final int position) {
            final String picture = pictures.get(position).getImage();
            View pagerLayout = mLayoutInflater.inflate(R.layout.image_pager_layout, container, false);
            ImageView imageView = (ImageView) pagerLayout.findViewById(R.id.screenshot);

            if (shouldStartActivity) {    //if the calling class is novelDetails, setup click listeners
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, imageActivity.class);
                        intent.putExtra("POSITION", position);
                        intent.putExtra("IMAGE_URLS", pictures);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
            } else { //Otherwise, allow zooming of the image
                PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
            }

            Picasso.with(mContext).load(picture).into(imageView);
            container.addView(pagerLayout);
            return pagerLayout;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
