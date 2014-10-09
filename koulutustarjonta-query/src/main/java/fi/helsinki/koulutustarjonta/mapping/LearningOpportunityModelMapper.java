package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.LearningOpportunity;
import fi.helsinki.koulutustarjonta.dto.LearningOpportunityDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class LearningOpportunityModelMapper extends ModelMapper {
    public LearningOpportunityModelMapper(String apiEndpoint) {
        super();
        this.addMappings(new LearningOpportunityMap(apiEndpoint));
    }

    class LearningOpportunityMap extends PropertyMap<LearningOpportunity, LearningOpportunityDTO> {
        final Converter<List<String>, List<String>> expandApiEndpoint;

        protected LearningOpportunityMap(final String apiEndpoint) {
            super();
            this.expandApiEndpoint = new AbstractConverter<List<String>, List<String>>() {
                @Override
                protected List<String> convert(List<String> source) {
                    return source.stream()
                            .map(ao -> String.format("%s/hakukohde/%s", apiEndpoint, ao))
                            .collect(Collectors.toList());
                }
            };
        }

        @Override
        protected void configure() {
            map().setTranslations(source.getGoals().availableTranslations());
            using(expandApiEndpoint).map().setApplicationOptions(source.getApplicationOptions());
        }
    }
}
