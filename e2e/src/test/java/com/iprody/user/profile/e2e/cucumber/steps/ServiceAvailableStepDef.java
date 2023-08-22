package com.iprody.user.profile.e2e.cucumber.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.iprody.user.profile.e2e.cucumber.JsonSerializationHelper;
import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.generated.api.ActuatorApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public final class ServiceAvailableStepDef {

    /**
     * Injection of HTTP client working with actuator endpoints.
     */
    private final ActuatorApi actuatorApi;
    /**
     * Helper bean for json (de)serialization.
     */
    private final JsonSerializationHelper jsonHelper;

    /**
     * Checking if health of service is good.
     */
    @Given("User Profile Service is up and running")
    public void upServiceIsUpAndRunning() {
        final var health = actuatorApi.health();
        final var actualStatus = jsonHelper.getObjectAsNode(health).get("status").asText();

        assertThat(actualStatus).isEqualTo("UP");
    }

    /**
     * Checking if specific user endpoint available.
     *
     * @param url        of tested endpoint e.g. "/users", "/users/{id}" ...
     * @param httpMethod of tested endpoint e.g. "POST", "put", "PUT", "GET"... (case insensitive)
     */
    @And("User endpoint {string} with http method {string} available")
    public void userEndpointAvailable(String url, String httpMethod) {
        actuatorApi.getApiClient().setBearerToken(TestContextStorage.getAccessToken());
        final var mappings = actuatorApi.mappings();
        final var actualEndpoints = jsonHelper.getObjectAsNode(mappings)
                .get("contexts")
                .get("application")
                .get("mappings")
                .get("dispatcherHandlers")
                .get("webHandler")
                .findValues("predicate")
                .stream()
                .map(JsonNode::asText)
                .toList();
        final var expectedEndpoint = "{%s %s}".formatted(httpMethod.toUpperCase(), url.toLowerCase());

        assertThat(actualEndpoints).contains(expectedEndpoint);
    }
}
