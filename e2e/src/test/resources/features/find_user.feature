Feature: Find User

  Background:
    Given User Profile Service is up and running
    And User endpoint "/users/{id}" with http method "GET" available

  Scenario: Find user successfully
    When a client wants to find a user with id 3
    Then response code is 200
    And response body contains:
      | id                      | 3                      |
      | firstName               | James                  |
      | lastName                | Williams               |
      | email                   | jamewilliams@gmail.com |
      | userDetails.telegramId  | jamestg                |
      | userDetails.mobilePhone | +1234567663            |
      | userDetails.userId      | 3                      |
      | userDetails.id          | 3                      |

  Scenario: Client error while no user found
    When a client wants to find a user with id 100
    Then response code is 404
    And response body contains:
      | status  | 404                        |
      | message | Resource was not found     |
      | details | No user found with id: 100 |