package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.Requirement;
import fi.helsinki.koulutustarjonta.dto.I18NDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

/**
 * @author Hannu Lyytikainen
 */
public class ApplicationOptionModelMapper extends ModelMapper {
    public ApplicationOptionModelMapper() {
        super();
        this.addConverter(new RequirementConverter());
    }

    class RequirementConverter extends AbstractConverter<Requirement, I18NDTO> {
        @Override
        protected I18NDTO convert(Requirement source) {
            return map(source.getDescription(), I18NDTO.class);
        }
    }
}
