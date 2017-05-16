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
        return type;
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
}
