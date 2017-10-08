package ecez.vndbapp.model;

import java.io.Serializable;

/**
 * Created by johnteng on 2017-05-04.
 */

public class Release implements Serializable {
    Integer id, minage;
    String title, original, released, type, website, notes, gtin, catalog;
    Boolean patch, freeware, doujin;
    String [] languages, platforms;
    ReleaseMedia [] media;
    ReleaseVN [] vn;
    ReleaseProducer [] producers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        if (original == null)
            return "N/A";
        return original;
    }

    public String getAgeRating(){
        if (minage == null)
            return "N/A";
        if (minage == 0)
             return "All Ages";
        return Integer.toString(minage) + "+";
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getType() {
        switch (type) {
            case "complete":
                return "Complete Release";
            case "partial":
                return "Partial Release";
            case "trial":
                return "Trial Edition";
            default:
                return "N/A";
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        if (notes == null)
            return "";
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Boolean getPatch() {
        return patch;
    }

    public void setPatch(Boolean patch) {
        this.patch = patch;
    }

    public Boolean getFreeware() {
        return freeware;
    }

    public void setFreeware(Boolean freeware) {
        this.freeware = freeware;
    }

    public Boolean getDoujin() {
        return doujin;
    }

    public void setDoujin(Boolean doujin) {
        this.doujin = doujin;
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

    public void setPlatforms(String[] platforms) {
        this.platforms = platforms;
    }

    public ReleaseMedia[] getMedia() {
        return media;
    }

    public void setMedia(ReleaseMedia[] media) {
        this.media = media;
    }

    public ReleaseVN[] getVn() {
        return vn;
    }

    public void setVn(ReleaseVN[] vn) {
        this.vn = vn;
    }

    public ReleaseProducer[] getProducers() {
        return producers;
    }

    public void setProducers(ReleaseProducer[] producers) {
        this.producers = producers;
    }

    public String getDate () {
        if (released.equals("tba"))
            return "To be announced";
        StringBuilder a = new StringBuilder();
        String year = released.substring(0, 4);
        String month = "", day = "";
        if (released.length() > 7)
            month = released.substring(5, 7);
        if (released.length() > 10)
            day = released.substring(8, 10);

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
            default:
                break;
        }
        if (day.length() > 0) {
            if (day.charAt(0) == '0') {
                a.append(day.charAt(1));
            } else {
                a.append(day);
            }
            a.append(", ");
        }
        a.append(year);
        return a.toString();
    }
}
