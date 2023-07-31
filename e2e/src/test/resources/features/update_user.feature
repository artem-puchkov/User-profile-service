Feature: Update user

  Background:
    Given User Profile Service is up and running
    And User endpoint "/users/{id}" with http method "PUT" available

  Scenario: User updated successfully with valid data for update
    When a client wants to update a user with id 1
    And wants to update a user to:
      | firstName               | first_updated     |
      | lastName                | last_updated      |
      | email                   | email@updated.com |
      | userDetails.telegramId  | @tg_updated       |
      | userDetails.mobilePhone | +77777777         |
    Then response code is 200
    And response body contains:
      | id                      | 1                 |
      | firstName               | first_updated     |
      | lastName                | last_updated      |
      | email                   | email@updated.com |
      | userDetails.telegramId  | @tg_updated       |
      | userDetails.mobilePhone | +77777777         |
      | userDetails.userId      | 1                 |
      | userDetails.id          | 1                 |

  Scenario: Internal error while updating a user if email already exists
    When a client wants to update a user with id 2
    And wants to update a user to:
      | firstName               | first_updated     |
      | lastName                | last_updated      |
      | email                   | email@updated.com |
      | userDetails.telegramId  | @tg_updated       |
      | userDetails.mobilePhone | +77777777         |
    Then response code is 500
    And response body contains:
      | status  | 500                                                  |
      | message | Error occurred during processing the resource        |
      | details | A user with email: email@updated.com already exists. |


  Scenario Outline: Client error while updating a user with invalid parameters
    When a client wants to update a user with id 1
    And wants to update a user to:
      | firstName               | <firstName>               |
      | lastName                | <lastName>                |
      | email                   | <email>                   |
      | userDetails.telegramId  | <userDetails.telegramId>  |
      | userDetails.mobilePhone | <userDetails.mobilePhone> |
    Then response code is 400
    And response body contains:
      | status  | 400                               |
      | message | Request validation error occurred |
      | details | <errorDetails>                    |
    Examples:
      | firstName                                 | lastName                                    | email             | userDetails.telegramId | userDetails.mobilePhone | errorDetails                                                                                                                                                                              |
      |                                           | last_updated                                | email@updated.com | @tg_updated            | +77777777               | First name should not be empty                                                                                                                                                            |
      | first_name_with_length_bigger_than_thirty | last_updated                                | email@updated.com | @tg_updated            | +77777777               | First name should not be longer than 30                                                                                                                                                   |
      | first_updated                             |                                             | email@updated.com | @tg_updated            | +77777777               | Last name should not be empty                                                                                                                                                             |
      | first_updated                             | last_updated_with_length_bigger_than_thirty | email@updated.com | @tg_updated            | +77777777               | Last name should not be longer than 30                                                                                                                                                    |
      | first_updated                             | last_updated                                |                   | @tg_updated            | +77777777               | Email field should not be empty                                                                                                                                                           |
      | first_updated                             | last_updated                                | email@updated     | @tg_updated            | +77777777               | Email field should be like: user@domain.com                                                                                                                                               |
      | first_updated                             | last_updated                                | email@updated.com |                        | +77777777               | Telegram field should not be empty                                                                                                                                                        |
      | first_updated                             | last_updated                                | email@updated.com | @tg_updated            | a77777777               | Mobile phone number is incorrect                                                                                                                                                          |
      |                                           | last_updated_with_length_bigger_than_thirty | email@notvalid    |                        | a77777777               | First name should not be empty; Last name should not be longer than 30; Email field should be like: user@domain.com; Telegram field should not be empty; Mobile phone number is incorrect |

  Scenario: Client error while updating non-existing user
    When a client wants to update a user with id 100
    And wants to update a user to:
      | firstName               | first_updated     |
      | lastName                | last_updated      |
      | email                   | email@updated.com |
      | userDetails.telegramId  | @tg_updated       |
      | userDetails.mobilePhone | +77777777         |
    Then response code is 404
    And response body contains:
      | status  | 404                        |
      | message | Resource was not found     |
      | details | No user found with id: 100 |