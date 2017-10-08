package ecez.vndbapp.model;

import android.util.Log;

/**
 * Created by Teng on 10/27/2016.
 */
public class DetailsData extends NovelData {

    private String description;
    private String aliases;

    private String original;
    private String [] languages, platforms;
    private String [][] tags;
    private NovelAnime [] anime;

    private NovelScreenShot [] screens;

    public DetailsData() {}

    public DetailsData(String title, Double rating, int length, String image,
                       String id, String [] languages, String [] platforms, String description,
                       Double popularity, int votecount, String released, String aliases) {
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
        this.aliases = aliases;
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

    public String getDescriptionWithoutBrackets () { return this.removeSourceBrackets(description).trim();}

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

    public NovelScreenShot[] getScreens() {
        return screens;
    }

    public void setScreens(NovelScreenShot[] screens) {
        this.screens = screens;
    }

    public NovelAnime[] getAnime() {
        if (anime == null)
            return new NovelAnime[0];
        return anime;
    }

    public void setAnime(NovelAnime[] anime) {
        this.anime = anime;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getOriginal() {
        if (original == null || original.equals(""))
            return title;
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getDate () {
        Log.d("Date",released);
        if (released.equals("tba"))
            return "To Be Announced";
        StringBuilder a = new StringBuilder();
        String year = released.substring(0, 4);
        if (released.length() == 4)
            return year;

        String month = released.substring(5, 7);
        switch (month) {
            case "01":
                a.append("January ");
                break;
            case "02":
                a.append("February ");
                break;
            case "03":
                a.append("March ");
                break;
            case "04":
                a.append("April ");
                break;
            case "05":
                a.append("May ");
                break;
            case "06":
                a.append("June ");
                break;
            case "07":
                a.append("July ");
                break;
            case "08":
                a.append("August ");
                break;
            case "09":
                a.append("September ");
                break;
            case "10":
                a.append("October ");
                break;
            case "11":
                a.append("November ");
                break;
            case "12":
                a.append("December ");
                break;
        }
        if (released.length() == 7) {
            a.append(year);
            return a.toString();
        }

        String day = released.substring(8, 10);
        if (day.charAt(0) == '0') {
            a.append(day.charAt(1));
        } else {
            a.append(day);
        }
        a.append(", "+year);
        return a.toString();
    }
}

