package com.iprody.user.profile.e2e.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Class is StepDefinition for Scenario Outline: Send a two valid and one invalid ID.
 */
public class UserApiWithTwoValidAndOneInvalidId {

    /**
     * Method for step When in Scenario.
     * @param id - users id
     */
    @When("send a request with id {long}")
    public void sendARequestWithIdId(long id) {
    }

    /**
     * Method for step Then in Scenario.
     * @param code - http status code in response
     */
    @Then("response has status code {int}")
    public void responseHasStatusCodeCode(int code) {
    }
}
