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
    public int rank;

    public listItem (String title, Double rating, int length, String image, int rank) { //Temporary constructor
        this.title = title;
        this.rating = rating;
        this.length = length;
        this.image = image;
        this.rank = rank;
        Log.d("listItem image",image);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        if (rating > 9){
            return rating.toString() + " (excellent)";
        } else if (rating > 8) {
            return rating.toString() + " (very good)";
        } else if (rating > 7) {
            return rating.toString() + " (good)";
        } else if (rating > 6) {
            return rating.toString() + " (decent)";
        } else {
            return rating.toString() + " (poor)";
        }
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getLength() {
        switch (length) {
            case 1:
                return "Very short (< 2 hours)";
            case 2:
                return "Short (2 - 10 hours)";
            case 3:
                return "Medium (10 - 30 hours)";
            case 4:
                return "Long (30 - 50 hours)";
            default:
                return "Very Long (> 50 hours)";
        }
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getImage() {
        return image;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getRank() {
        return rank;
    }
//    public void setImageResourceId(String imageResourceId) {
//        this.image = imageResourceId;
//    }
}
