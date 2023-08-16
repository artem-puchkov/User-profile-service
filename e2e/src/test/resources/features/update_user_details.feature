Feature: Update user details

  Background:
    Given User Profile Service is up and running
    And User endpoint "/users/{id}/details" with http method "PUT" available

  Scenario: User details updated successfully with valid data for update
    When a client wants to update user details with user ID 2
    And wants to update user details to:
      | telegramId  | @tg_updated |
      | mobilePhone | +77777777   |
    Then response code is 200
    And response body contains:
      | telegramId  | @tg_updated |
      | mobilePhone | +77777777   |
      | userId      | 2           |
      | id          | 2           |

  Scenario Outline: Client error while updating user details with invalid parameters
    When a client wants to update user details with user ID 2
    And wants to update user details to:
      | telegramId  | <telegramId>  |
      | mobilePhone | <mobilePhone> |
    Then response code is 400
    And response body contains:
      | status  | 400                               |
      | message | Request validation error occurred |
      | details | <errorDetails>                    |
    Examples:
      | telegramId  | mobilePhone | errorDetails                       |
      |             | +77777777   | Telegram field should not be empty |
      | @tg_updated | a77777777   | Mobile phone number is incorrect   |


  Scenario: Client error while updating non-existing user details
    When a client wants to update user details with user ID 100
    And wants to update user details to:
      | telegramId  | @tg_updated |
      | mobilePhone | +77777777   |
    Then response code is 404
    And response body contains:
      | status  | 404                                   |
      | message | Resource was not found                |
      | details | No userDetails found with user_id 100 |