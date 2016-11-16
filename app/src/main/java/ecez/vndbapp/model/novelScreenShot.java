package ecez.vndbapp.model;

/**
 * Created by Teng on 11/1/2016.
 */
public class novelScreenShot {
    int rid,height,width;
    Boolean nsfw;
    String image;

    public novelScreenShot (int rid, int height, int width, Boolean nsfw, String image) {
        this.rid = rid;
        this.height = height;
        this.width = width;
        this.nsfw = nsfw;
        this.image = image;
    }

    public novelScreenShot(String image) {
        this.image = image;
    }

    public String getImage () {
        return this.image;
    }
}
