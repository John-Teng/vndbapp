package ecez.vndbapp.model;

import java.io.Serializable;

/**
 * Created by Teng on 12/6/2016.
 */
public class Trait extends DumpObject implements Serializable {
    Integer chars;

    public Integer getChars() {
        return chars;
    }
    public void setChars(Integer chars) {
        this.chars = chars;
    }

}
