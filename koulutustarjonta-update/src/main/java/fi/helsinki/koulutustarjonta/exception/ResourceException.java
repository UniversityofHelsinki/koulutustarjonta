package fi.helsinki.koulutustarjonta.exception;

public class ResourceException extends RuntimeException {

    private String oid;
    private Class clazz;

    public ResourceException(String oid, Class<?> clazz) {
        this.oid = oid;
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public String getOid() {
        return oid;
    }
}
