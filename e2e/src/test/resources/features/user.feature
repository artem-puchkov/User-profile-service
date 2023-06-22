Feature: User api test

  Scenario: Call GET reuest with valid ID
    Given call GET to URI "/users/":
    When path param ID <1>
    Then status code <200>

  Scenario Outline: Send a two valid and one invalid ID
    When send a request with id <id>
    Then response has status code <code>
    Examples:
      | id   | code |
      | 1    | 200  |
      | 2    | 200  |
      | 1000 | 403  |