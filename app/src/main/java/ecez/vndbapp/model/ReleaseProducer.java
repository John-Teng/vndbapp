package ecez.vndbapp.model;

import java.io.Serializable;

/**
 * Created by johnteng on 2017-05-04.
 */

public class ReleaseProducer implements Serializable {
    public Integer id;
    public Boolean developer, publisher;
    public String name, original, type;
}
