package fi.helsinki.koulutustarjonta.domain;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationSystem {

    private String oid;
    private I18N name;
    private I18N applicationMethod;
    private String applicationYear;

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

    public I18N getApplicationMethod() {
        return applicationMethod;
    }

    public void setApplicationMethod(I18N applicationMethod) {
        this.applicationMethod = applicationMethod;
    }

    public String getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(String applicationYear) {
        this.applicationYear = applicationYear;
    }
}
