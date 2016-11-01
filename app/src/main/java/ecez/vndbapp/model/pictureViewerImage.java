package ecez.vndbapp.model;

/**
 * Created by Teng on 10/31/2016.
 */
public class pictureViewerImage {
    private String image;

    public pictureViewerImage (String imageURL) {
        this.image = imageURL;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imageURL) {
        this.image = imageURL;
    }

}
