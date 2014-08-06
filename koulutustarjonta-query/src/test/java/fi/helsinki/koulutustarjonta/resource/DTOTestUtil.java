package fi.helsinki.koulutustarjonta.resource;

import fi.helsinki.koulutustarjonta.dto.I18NDTO;

import static org.junit.Assert.assertEquals;

/**
 * @author Hannu Lyytikainen
 */
public class DTOTestUtil {

    public static void i18NDTOsEqual(I18NDTO expected, I18NDTO actual) {
        assertEquals(expected.getFi(), actual.getFi());
        assertEquals(expected.getSv(), actual.getSv());
        assertEquals(expected.getEn(), actual.getEn());

    }


}
