Feature: Create User with valid parameters

Scenario:
  Given User Profile Service is up and running

  And User endpoint "/users" with http method "POST" available

  When a client wants create user with parameters:
    | firstName               | Agent          |
    | lastName                | Smith          |
    | email                   | smith@mail.com |
    | userDetails.telegramId  | @telegram42    |
    | userDetails.mobilePhone | +49333333642   |

  Then response code is 201

  And response body contains:
    | firstName               | Agent          |
    | lastName                | Smith          |
    | email                   | smith@mail.com |
    | userDetails.telegramId  | @telegram42    |
    | userDetails.mobilePhone | +49333333642   |