package ecez.vndbapp.model;

import java.io.Serializable;

/**
 * Created by Teng on 11/1/2016.
 */
public class NovelScreenShot implements Serializable {
    private int rid,height,width;
    private Boolean nsfw;
    private String image;

    public NovelScreenShot() {}

    public NovelScreenShot(int rid, int height, int width, Boolean nsfw, String image) {
        this.rid = rid;
        this.height = height;
        this.width = width;
        this.nsfw = nsfw;
        this.image = image;
    }

    public NovelScreenShot(String image) {
        this.image = image;
    }

    public String getImage () {
        return this.image;
    }

    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }
}
