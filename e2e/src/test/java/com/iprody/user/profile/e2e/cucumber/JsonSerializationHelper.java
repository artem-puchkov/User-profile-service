package com.iprody.user.profile.e2e.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.iprody.user.profile.e2e.cucumber.FieldNamesConst.*;

@RequiredArgsConstructor
@Component
public class JsonSerializationHelper {

    /**
     * Injecting bean with json parsing functionality.
     */
    private final ObjectMapper objectMapper;

    /**
     * @param object we want convert to JsonNode
     * @return object as JsonNode
     */
    public JsonNode getObjectAsNode(Object object) {
        try {
            final var objectAsJson = objectMapper.writeValueAsString(object);
            return objectMapper.readTree(objectAsJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Something happened during serialization", e);
        }
    }

    public JsonNode getResponseBodyAsJsonNode() {
        return objectMapper.valueToTree(TestContextStorage.getResponseBody());
    }

    public Map<String, String> jsonNodeToUserFieldsMap(JsonNode responseBody) {
        return responseBody.properties().stream()
                .filter(e -> !e.getKey().toLowerCase().startsWith(USER_DETAILS_FIELD_NAME))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().asText()));
    }

    public Map<String, String> jsonNodeToDetailsFieldsMap(JsonNode responseBody) {
        return responseBody.get(USER_DETAILS_JSON_NAME).properties().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().asText()));
    }

    public Map<String, Object> jsonNodeToErrorFieldsMap(JsonNode responseBody) {
        return responseBody.properties().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase(ERROR_DETAILS_NAME))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().asText()));
    }

    public List<String> jsonNodeToErrorDetailsList(JsonNode responseBody) {
        return objectMapper.convertValue(responseBody.get(ERROR_DETAILS_NAME),new TypeReference<ArrayList<String>>(){});
    }
}
