package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class SearchResultConverter {

    public List<String> convert(JsonNode apiResult) {
        return Lists.newArrayList(apiResult.get("result")
                .get("tulokset"))
                .stream()
                .map(provider ->
                                Lists.newArrayList(provider.get("tulokset"))
                                        .stream()
                                        .map(result -> result.get("oid").textValue())
                                        .collect(toList())
                )
                .flatMap(oidList -> oidList.stream())
                .collect(toList());
    }
}
