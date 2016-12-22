package ecez.vndbapp.model;

import java.io.Serializable;

/**
 * Created by Teng on 12/21/2016.
 */
public class Tag extends DumpObject implements Serializable {

    Integer vns;
    String cat;

    public Integer getVns() {
        return vns;
    }

    public void setVns(Integer vns) {
        this.vns = vns;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
