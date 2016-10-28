package ecez.vndbapp.model;

/**
 * Created by Teng on 10/27/2016.
 */
public class detailsData {

    private String title;
    private Double rating;
    private int length;
    private String image;
    public String id;
    private String [] languages;
    private String [] platforms;
    private String description;
    private double popularity;
    private int votecount;
    public int rank;

    public detailsData (String title, Double rating, int length, String image, int rank, String id, String [] languages, String [] platforms, String description, Double popularity, int votecount) {
        this.title = title;
        this.rating = rating;
        this.length = length;
        this.image = image;
        this.rank = rank;
        this.id = id;
        this.languages = languages;
        this.platforms = platforms;
        this.description = description;
        this.popularity = popularity;
        this.votecount = votecount;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String [] platforms) {
        this.platforms = platforms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return votecount;
    }

    public void setVoteCount(int votecount) {
        this.votecount = votecount;
    }

    public String getId() {
        return id;
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

}

