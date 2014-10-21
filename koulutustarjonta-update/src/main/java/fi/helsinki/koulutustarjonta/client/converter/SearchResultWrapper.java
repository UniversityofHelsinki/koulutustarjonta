package fi.helsinki.koulutustarjonta.client.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Hannu Lyytikainen
 */
public class SearchResultWrapper {

    private final JsonNode rootNode;

    public SearchResultWrapper(JsonNode apiResult) {
        this.rootNode = apiResult;
    }

    public List<String> getOids() {
        return Lists.newArrayList(rootNode.get("result")
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
