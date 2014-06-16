package fi.helsinki.koulutustarjonta.client.converter;


import fi.helsinki.koulutustarjonta.domain.I18N;
import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;

import java.util.Map;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityConverter extends BaseConverter {

    public LearningOpportunity convert(Map<String, Object> apiCallResult) {

        LearningOpportunity learningOpportunity = new LearningOpportunity();
        Map<String, Object> resutlContent = getObjectMap(apiCallResult, "result");
        Map<String, String> goals = getStringMap(
                getObjectMap(
                        getObjectMap(resutlContent, "kuvausKomo"),
                        "TAVOITTEET"),
                "tekstis"
        );
        learningOpportunity.setGoals(new I18N(goals.get("kieli_fi"), null, null));

        return learningOpportunity;
    }


}
