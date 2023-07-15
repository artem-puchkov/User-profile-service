package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.cucumber.assertion.FactoryDtoAssertHandler;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StepDefinition that compares data set in feature file and responseBody.
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class HttpResponseStep {

    FactoryDtoAssertHandler factoryDtoAssertHandler;

    /**
     * Check http status in feature file and response status.
     *
     * @param expectedCode - status code in feature file
     */
    @Then("response code is {int}")
    public void responseCodeIs(int expectedCode) {
        final int actualCode = TestContextStorage.getStatus();
        assertThat(actualCode).isEqualTo(expectedCode);
    }

    /**
     * Check dataTable in feature file and responseBody.
     *
     * @param dataTable - userDto parameters in feature file
     */
    @And("response body contains:")
    public void responseBodyContains(DataTable dataTable) {
        factoryDtoAssertHandler.getInstance(TestContextStorage.getResponseBodyType()).assertResponseBody(dataTable);
    }
}
