package ecez.vndbapp.model;

/**
 * Created by johnteng on 2017-05-04.
 */

public class Producer {
    Integer ID;
    String name, original, type, language, aliases, description;
    ProducerLinks links;
    ProducerRelation [] relations;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public ProducerLinks getLinks() {
        return links;
    }

    public void setLinks(ProducerLinks links) {
        this.links = links;
    }

    public ProducerRelation[] getRelations() {
        return relations;
    }

    public void setRelations(ProducerRelation[] relations) {
        this.relations = relations;
    }
}
