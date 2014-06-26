package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.dto.LearningOpportunityDTO;
import org.modelmapper.PropertyMap;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityMap extends PropertyMap<LearningOpportunity, LearningOpportunityDTO> {
    @Override
    protected void configure() {
        map().setTranslations(source.getGoals().availableTranslations());
    }
}
