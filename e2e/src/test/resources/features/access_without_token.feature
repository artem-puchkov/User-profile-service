Feature: Failed Access

  Background:
    Given User Profile Service is up and running
    And User endpoint "/users/{id}" with http method "GET" available

  Scenario: Access to secured endpoint withot jwt token
    Given client does not have a token
    When a client wants to find a user with id 3
    Then response code is 401
    And response body contains:
      | message | UNAUTHORIZED      |
      | status  | 401               |
      | details | Not Authenticated |