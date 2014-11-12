package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.Organization;
import fi.helsinki.koulutustarjonta.dto.OrganizationDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

/**
 * @author Hannu Lyytikainen
 */
public class OrganizationModelMapper extends ModelMapper {

    public OrganizationModelMapper() {
        super();
        this.addMappings(new OrganizationMap());
    }

    class OrganizationMap extends PropertyMap<Organization, OrganizationDTO> {
        @Override
        protected void configure() {
            map().setTranslations(source.getName().availableTranslations());
        }
    }
}
