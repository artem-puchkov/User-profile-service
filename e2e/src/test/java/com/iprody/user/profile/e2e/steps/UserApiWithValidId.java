package com.iprody.user.profile.e2e.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Class is StepDefinition for Scenario: Call GET reuest with valid ID.
 */
public class UserApiWithValidId {

    /**
     * Method for step Given in Scenario.
     * @param uri - path to endpoint
     */
    @Given("call GET to URI {string}:")
    public void callGETToURI(String uri) {
    }

    /**
     * Method for step When in Scenario.
     * @param id - users id
     */
    @When("path param ID <{long}>")
    public void pathParamID(long id) {
    }

    /**
     * Method for step Then in Scenario.
     * @param code - http status code in response
     */
    @Then("status code <{int}>")
    public void statusCode(int code) {
    }
}
