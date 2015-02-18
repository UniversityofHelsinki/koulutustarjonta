package fi.helsinki.koulutustarjonta.mapping;

import fi.helsinki.koulutustarjonta.domain.ApplicationSystem;
import fi.helsinki.koulutustarjonta.dto.ApplicationSystemDTO;
import fi.helsinki.koulutustarjonta.test.Fixture;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class ApplicationSystemModelMapperTest {

    private final ModelMapper modelMapper = new ApplicationSystemModelMapper();

    @Test
    public void thatApplicationFormUrlIsSetFromOpintopolkuFormUrl() {
        ApplicationSystem applicationSystem = Fixture.applicationSystemWithoutApplicationForm("1.2.3");
        ApplicationSystemDTO applicationSystemDTO = modelMapper.map(applicationSystem, ApplicationSystemDTO.class);

        assertEquals(applicationSystem.getOpintopolkuFormUrl(), applicationSystemDTO.getApplicationFormUrl());
    }

    @Test
    public void thatApplicationFormUrlIsSetFromApplicationFormUrl() {
        ApplicationSystem applicationSystem = Fixture.applicationSystemWithApplicationForm("1.2.3");
        ApplicationSystemDTO applicationSystemDTO = modelMapper.map(applicationSystem, ApplicationSystemDTO.class);

        assertEquals(applicationSystem.getApplicationFormUrl(), applicationSystemDTO.getApplicationFormUrl());
    }
}
