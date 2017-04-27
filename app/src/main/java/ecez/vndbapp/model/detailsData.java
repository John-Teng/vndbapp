package ecez.vndbapp.model;

/**
 * Created by Teng on 10/27/2016.
 */
public class DetailsData extends NovelData {

    private String description;
    private String [] languages, platforms;
    private String [][] tags;

    public DetailsData() {}

    public DetailsData(String title, Double rating, int length, String image,
                       String id, String [] languages, String [] platforms, String description,
                       Double popularity, int votecount, String released) {
        this.title = title;
        this.rating = rating;
        this.length = length;
        this.image = image;
        this.id = id;
        this.languages = languages;
        this.platforms = platforms;
        this.description = description;
        this.popularity = popularity;
        this.votecount = votecount;
        this.released = released;
    }

    public String[][] getTags() {
        return tags;
    }

    public void setTags(String[][] tags) {
        this.tags = tags;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
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

    public String getDescriptionWithoutBrackets () { return this.removeSourceBrackets(description);}

    public static String removeSourceBrackets (String s) { //Refactor to the model layer
        int openBraceCount = 0, closeBraceCount = 0, startSearchPosition, braceStartPosition, nextOpenBrace, nextClosedBrace;
        Boolean removed = false;
        if (s == null || s.equals(""))
            return "";

        while (s.contains("[")&&s.contains("]")) { //repeat while the string contains braces
            braceStartPosition = startSearchPosition = s.indexOf("[");
            openBraceCount++;

            while (!removed) {
                nextOpenBrace = s.indexOf("[", startSearchPosition+1);
                nextClosedBrace = s.indexOf("]", startSearchPosition+1);

                if (nextClosedBrace == -1) //Not the same number of open braces as closed braces
                    return "The passed string does not have the same number of opening braces as closing braces";
                else if (nextOpenBrace == -1) { //There are no more open braces in the string
                    closeBraceCount ++;
                    startSearchPosition = nextClosedBrace;
                }
                else if (nextOpenBrace < nextClosedBrace) {      //There are 2 open braces in a row
                    startSearchPosition = nextOpenBrace;
                    openBraceCount++;
                } else { //Next is a closed brace
                    closeBraceCount++;
                    startSearchPosition = nextClosedBrace;
                }
                if (closeBraceCount == openBraceCount) {
                    String removeMe = s.substring(braceStartPosition, nextClosedBrace+1);
                    s = s.replace(removeMe, "");
                    removed = true;
                }
            }
            removed = false;
            openBraceCount = closeBraceCount = 0;
        }
        return s;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setVoteCount(int votecount) {
        this.votecount = votecount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setLength(int length) {
        this.length = length;
    }



}

