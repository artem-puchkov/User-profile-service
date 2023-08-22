Feature: Login User

  Background:
    Given User Profile Service is up and running
    And User endpoint "/auth" with http method "POST" available

  Scenario: Login with valid email and password
    When a client wants login with email "jamewilliams@gmail.com" and password "123456"
    Then response code is 200
    And response body contains access token and refresh token
    And a client adds a Authorization header from response
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
    And Clear Authorization header for other tests