package fi.helsinki.koulutustarjonta.client.converter;

import fi.helsinki.koulutustarjonta.core.domain.Degree;
import fi.helsinki.koulutustarjonta.core.domain.I18N;

import java.util.Map;

/**
 * @author Hannu Lyytikainen
 */
public class DegreeConverter extends BaseConverter {

    public Degree convert(Map<String, Object> apiCallResult) {

        Degree degree = new Degree();
        Map<String, Object> resutlContent = getObjectMap(apiCallResult, "result");
        Map<String, String> goals = getStringMap(
                getObjectMap(
                        getObjectMap(resutlContent, "kuvausKomo"),
                        "TAVOITTEET"),
                "tekstis"
        );
        degree.setGoals(new I18N(goals.get("kieli_fi"), null, null));

        return degree;
    }


}
