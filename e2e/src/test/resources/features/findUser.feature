Feature: Find User

  Background:
    Given User Profile Service is up and running
    And User endpoint "/users/{id}" with http method "GET" available

  Scenario: Find user successfully
    When a client wants to find a user with id 1
    Then response code is 200
    And response body contains:
      | id                      | 1                         |
      | firstName               | Gregory                   |
      | lastName                | House                     |
      | email                   | housegregory213@gmail.com |
      | userDetails.telegramId  | gregtg                    |
      | userDetails.mobilePhone | +1234567890               |
      | userDetails.userId      | 1                         |
      | userDetails.id          | 1                         |

  Scenario: Client error while no user found
    When a client wants to find a user with id 100
    Then response code is 404
    And response body contains:
      | status  | 404                        |
      | message | Resource was not found     |
      | details | No user found with id: 100 |