package fi.helsinki.koulutustarjonta.domain;

/**
 * @author Hannu Lyytikainen
 */
public class Organization {

    public String oid;
    public I18N name;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public I18N getName() {
        return name;
    }

    public void setName(I18N name) {
        this.name = name;
    }
}
