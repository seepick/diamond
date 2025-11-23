Feature: feature name 2

  Scenario: scenario name 2
    When get home page
    Then response status code is 200
    And response body is "Hello Service!"
