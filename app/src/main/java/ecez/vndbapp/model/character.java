package ecez.vndbapp.model;

/**
 * Created by Teng on 11/20/2016.
 */
public class character {
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

    public Integer getBust() {
        return bust;
    }

    public void setBust(Integer bust) {
        this.bust = bust;
    }

    public Integer getWaist() {
        return waist;
    }

    public void setWaist(Integer waist) {
        this.waist = waist;
    }

    public Integer getHip() {
        return hip;
    }

    public void setHip(Integer hip) {
        this.hip = hip;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

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
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodt() {
        return bloodt;
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

    public Integer[] getBirthday() {
        return birthday;
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
        int x;

        for (x = 0; x< s.length; x++) {
            if (Integer.parseInt(s[x][0]) == id) {
                break;
            }
        }
        switch (s[x][3]) {
            case "main":
                return "Protagonist";
            case "primary":
                return "Main character";
            case "side":
                return "Side Character";
            case "appears":
                return "Makes an appearance";
            default:
                return "";
        }
    }

    public void setVns(String[][] vns) {
        this.vns = vns;
    }

    public character () {

    }


}
