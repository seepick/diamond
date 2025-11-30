Feature: feature name

  Scenario: scenario name
    When get homepage
    Then the response status code is 200
    And the response body is "Hello Service!"
