package ecez.vndbapp.controller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ecez.vndbapp.R;
import ecez.vndbapp.controller.ImageActivity;
import ecez.vndbapp.model.NovelScreenShot;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Teng on 11/2/2016.
 */
public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private NovelScreenShot [] pictures;
    private Boolean shouldStartActivity = false;

    public ImagePagerAdapter(NovelScreenShot [] pictures, Context context, Boolean shouldStartActivity) {
        this(pictures, context);
        this.shouldStartActivity = shouldStartActivity;
    }

    public ImagePagerAdapter(NovelScreenShot [] pictures, Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pictures = pictures;
    }

    public void setImage (NovelScreenShot [] pictures) {
        this.pictures = pictures;
    }

    @Override
    public int getCount() {
        return pictures.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final String picture = pictures[position].getImage();
        View pagerLayout = mLayoutInflater.inflate(R.layout.image_pager_layout, container, false);
        final ImageView imageView = (ImageView) pagerLayout.findViewById(R.id.screenshot);

        if (shouldStartActivity) {    //if the touch gesture should open the ImageActivity, set the onclick Listener
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra("POSITION", position);
                    intent.putExtra("IMAGE_URLS", pictures);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
            Picasso.with(mContext).load(picture).into(imageView);
        } else { //Otherwise, allow zooming of the image
            Picasso.with(mContext).load(picture).into(imageView, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    new PhotoViewAttacher(imageView); //Must wait for image to load before attaching it to photoviewer
                }
                @Override
                public void onError() {

                }
            });
        }

        container.addView(pagerLayout);
        return pagerLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
