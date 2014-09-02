package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Code;
import io.dropwizard.testing.FixtureHelpers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Hannu Lyytikainen
 */
public abstract class AbstractClientConverterTest {

    protected JsonNode fixture(String file) throws IOException {
        String fixture = FixtureHelpers.fixture(file);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(fixture);
    }

    protected KoodistoClient mockKoodistoClient() {
        KoodistoClient koodistoClient = mock(KoodistoClient.class);

        // mock langs
        Code fi = new Code();
        fi.setValue("fi");
        Code sv = new Code();
        sv.setValue("sv");
        Code en = new Code();
        en.setValue("en");
        when(koodistoClient.getCode(startsWith("kieli_fi"))).thenReturn(fi);
        when(koodistoClient.getCode(startsWith("kieli_sv"))).thenReturn(sv);
        when(koodistoClient.getCode(startsWith("kieli_en"))).thenReturn(en);

        // mock postal codes
        when(koodistoClient.getCode(startsWith("posti_"))).thenAnswer(
                new Answer<Code>() {
                    @Override
                    public Code answer(InvocationOnMock invocation) throws Throwable {
                        String arg = (String) invocation.getArguments()[0];
                        Code code = new Code();
                        code.setValue(arg.split("_")[1]);
                        return code;
                    }
                }
        );
        return koodistoClient;
    }


}
