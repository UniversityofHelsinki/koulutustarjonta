package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import fi.helsinki.koulutustarjonta.client.KoodistoClient;
import fi.helsinki.koulutustarjonta.domain.Exam;
import fi.helsinki.koulutustarjonta.domain.ExamEvent;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hannu Lyytikainen
 */
public class ExamConverter extends BaseConverter {

    private final AddressConverter addressConverter;

    public ExamConverter(KoodistoClient koodistoClient) {
        super(koodistoClient);
        this.addressConverter = new AddressConverter(koodistoClient);
    }

    public Exam convert(JsonNode examJson) {
        Exam e = new Exam();
        e.setOid(examJson.get("oid").textValue());
        e.setLang(examJson.get("valintakokeenKuvaus").get("arvo").textValue().toLowerCase());
        e.setType(examJson.get("valintakoeNimi").textValue());
        e.setDescription(examJson.get("valintakokeenKuvaus").get("teksti").textValue());
        e.setEvents(convertEvents(examJson.get("valintakoeAjankohtas")));
        return e;
    }

    private List<ExamEvent> convertEvents(JsonNode eventArray) {
        return Lists.newArrayList(eventArray).stream()
                .map(event -> convertEvent(event))
                .collect(Collectors.toList());
    }

    private ExamEvent convertEvent(JsonNode eventJson) {
        ExamEvent event = new ExamEvent();
        event.setOid(eventJson.get("oid").textValue());
        event.setStarts(new Date(eventJson.get("alkaa").asLong()));
        event.setEnds(new Date(eventJson.get("loppuu").asLong()));
        event.setInfo(eventJson.get("lisatiedot").textValue());
        event.setAddress(addressConverter.convert(eventJson.get("osoite")));
        return event;
    }
}
