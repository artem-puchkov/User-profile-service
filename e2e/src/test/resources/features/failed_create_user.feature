Feature: Client error while creating a user with invalid parameters

  Scenario Outline: Create users with invalid parameters

    Given User Profile Service is up and running

    And User endpoint "/users" with http method "POST" available

    When a client wants create user with parameters:
      | id                      | <id>                      |
      | firstName               | <firstName>               |
      | lastName                | <lastName>                |
      | email                   | <email>                   |
      | password                | <password>                |
      | userDetails.telegramId  | <userDetails.telegramId>  |
      | userDetails.mobilePhone | <userDetails.mobilePhone> |

    Then response code is 400

    And response body contains:
      | status  | <status>                          |
      | message | Request validation error occurred |
      | details | <details>                         |

    Examples:
      | id | firstName | lastName | email            | password   | userDetails.telegramId | userDetails.mobilePhone | status | details                                                                                                                                    |
      |    |           | Smith    | smith@iprody.com | 12345Aa+   | @telega                | +99133333333            | 400    | First name should not be empty                                                                                                             |
      |    |           | Smith    | smith@iprody.com | 12345Aa+   |                        | +99133333333            | 400    | Telegram field should not be empty; First name should not be empty                                                                         |
      |    | Agent     | Smith    | smithiprody.com  | 12345Aa+   | @telega                | +99133333333            | 400    | Email field should be like: user@domain.com                                                                                                |
      |    | Agent     | Smith    | smith@iprody.com | 12345Aa+   | @telega                | 000000000033            | 400    | Mobile phone number is incorrect                                                                                                           |
      |    | Agent     | Smith    | smith@iprody.com | 12345Aa+   |                        | +99133333333            | 400    | Telegram field should not be empty                                                                                                         |
      |    | Agent     | Smith    | smith@iprody.com |            | @telega                | +99133333333            | 400    | Password should not be empty                                                                                                               |
      |    | Agent     | Smith    | smith@iprody.com | 123Av&     | @telega                | +99133333333            | 400    | Password must be at least 8 characters long and must contain at least one lowercase and uppercase letter, one digit and one special symbol |
      |    | Agent     | Smith    | smith@iprody.com | 1234567A   | @telega                | +99133333333            | 400    | Password must be at least 8 characters long and must contain at least one lowercase and uppercase letter, one digit and one special symbol |
      |    | Agent     | Smith    | smith@iprody.com | 1234567+   | @telega                | +99133333333            | 400    | Password must be at least 8 characters long and must contain at least one lowercase and uppercase letter, one digit and one special symbol |
      |    | Agent     | Smith    | smith@iprody.com | dfdfF+%abc | @telega                | +99133333333            | 400    | Password must be at least 8 characters long and must contain at least one lowercase and uppercase letter, one digit and one special symbol |

