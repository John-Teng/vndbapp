package ecez.vndbapp.model;

import android.util.Log;

/**
 * Created by Teng on 10/10/2016.
 */
public class listItem {

    private String title;
    private Double rating;
    private int length;
    private String image;


    public listItem (String title, Double rating, int length, String image) { //Temporary constructor
        this.title = title;
        this.rating = rating;
        this.length = length;
        this.image = image;
        Log.d("listItem image",image);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getImage() {
        return image;
    }

//    public void setImageResourceId(String imageResourceId) {
//        this.image = imageResourceId;
//    }
}
