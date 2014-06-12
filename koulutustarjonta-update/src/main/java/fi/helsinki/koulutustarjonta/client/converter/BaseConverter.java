package fi.helsinki.koulutustarjonta.client.converter;

import java.util.Map;

/**
 * @author Hannu Lyytikainen
 */
public abstract class BaseConverter {

    /**
     *  Takes a value matched by a key from a map
     *  and casts the return value to a <code>Map<String, Object>></code>
     *
     * @param map map
     * @return value as a map
     */
    Map<String, Object> getObjectMap(Map<String, Object> map, String key) {
        return (Map<String, Object>) map.get(key);
    }

    /**
     *  Takes a value matched by a key from a map
     *  and casts the return value to a <code>Map<String, String>></code>
     *
     * @param map map
     * @return value as a map
     */
    Map<String, String> getStringMap(Map<String, Object> map, String key) {
        return (Map<String, String>) map.get(key);
    }




}
