package ecez.vndbapp.model;

/**
 * Created by johnteng on 2017-05-04.
 */

public class Release {
    Integer id;
    String title, original, released, type, website, notes, gtin, catalog;
    Boolean patch, freeware, doujin;
    String [] languages, platforms;
    ReleaseMedia [] media;
    ReleaseVN [] vn;
    ReleaseProducer [] producers;
}
