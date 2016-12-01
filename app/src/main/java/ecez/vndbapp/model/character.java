package ecez.vndbapp.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Teng on 11/20/2016.
 */
public class Character implements Serializable {

    Integer id, bust, waist, hip, height, weight;
    String name, original, gender, bloodt, aliases, description, image;
    Integer [] birthday;
    String [][] traits, vns;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBust() {
        if (bust==null)
            return null;
        return bust+" cm";
    }

    public void setBust(Integer bust) {
        this.bust = bust;
    }

    public String getWaist() {
        if (waist==null)
            return null;
        return waist+" cm";
    }

    public void setWaist(Integer waist) {
        this.waist = waist;
    }

    public String getHip() {
        if (hip==null)
            return null;
        return hip+" cm";    }

    public void setHip(Integer hip) {
        this.hip = hip;
    }

    public String getHeight() {
        if (height==null)
            return null;
        return height+" cm";
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getWeight() {
        if (weight==null)
            return null;
        return weight+" kg";    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getGender() {
        if (gender==null)
            return null;
        switch (gender) {
            case "m":
                return "Male";
            case "f":
                return "Female";
            case "b":
                return "Both";
            default:
                return "Unknown";
        }
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodt() {
        if (bloodt==null)
            return null;
        return bloodt.toUpperCase();
    }

    public void setBloodt(String bloodt) {
        this.bloodt = bloodt;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthday() {
        if (birthday[0] == null || birthday[1]==null)
            return null;
        return birthday[1] + "/" + birthday[0];
    }

    public void setBirthday(Integer[] birthday) {
        this.birthday = birthday;
    }

    public String[][] getTraits() {
        return traits;
    }

    public void setTraits(String[][] traits) {
        this.traits = traits;
    }

    public String getRole(int id) {
        String [] [] s = this.vns;
        Log.d("vns", Integer.toString(id));
        for (int x = 0; x < s.length; x++) {
            if (Integer.parseInt(s[x][0]) == id) {
                Log.d("vns", s[x][3]);
                switch (s[x][3]) {
                    case "main":
                        return "Protagonist";
                    case "primary":
                        return "Main Character";
                    case "side":
                        return "Side Character";
                    case "appears":
                        return "Makes an appearance";
                    default:
                        return "";
                }
            }
        }
        return "";
    }

    public void setVns(String[][] vns) {
        this.vns = vns;
    }

    public Character() {

    }


}
