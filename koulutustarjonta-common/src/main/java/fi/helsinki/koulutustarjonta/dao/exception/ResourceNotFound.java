package fi.helsinki.koulutustarjonta.dao.exception;

/**
 * @author Hannu Lyytikainen
 */
public class ResourceNotFound extends Exception {
    public <T> ResourceNotFound(Class<T> type, String id) {
        super(String.format("Could not find resource of type %s  with identifier %s", type.toString(), id));
    }
}
