package ecez.vndbapp.model;

/**
 * Created by Teng on 10/10/2016.
 */
public class listItem {

    private String title;
    private String rating;
    private String length;
    private int imageResourceId;


    public listItem (String title, String rating, String length, int imageResourceId) { //Temporary constructor
        this.title = title;
        this.rating = rating;
        this.length = length;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
