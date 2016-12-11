package ecez.vndbapp.model;

import java.io.Serializable;

/**
 * Created by Teng on 12/6/2016.
 */
public class Trait implements Serializable {
    Integer id, chars;
    String name, description;
    Boolean meta;
    String [] aliases;
    Integer [] parents;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChars() {
        return chars;
    }

    public void setChars(Integer chars) {
        this.chars = chars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getMeta() {
        return meta;
    }

    public void setMeta(Boolean meta) {
        this.meta = meta;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public Integer[] getParents() {
        return parents;
    }

    public void setParents(Integer[] parents) {
        this.parents = parents;
    }
}
