Feature: Find User Details

  Background:
    Given User Profile Service is up and running
    And User endpoint "/users/{id}/details" with http method "GET" available

  Scenario: Find user details successfully
    When a client wants to find a user details with id 3
    Then response code is 200
    And response body contains:
      | telegramId  | jamestg     |
      | mobilePhone | +1234567663 |
      | userId      | 3           |
      | id          | 3           |

  Scenario: Client error while no user details found
    When a client wants to find a user details with id 100
    Then response code is 404
    And response body contains:
      | status  | 404                        |
      | message | Resource was not found     |
      | details | No userDetails found with user_id 100 |