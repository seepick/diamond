Feature: Crystal endpoint

  Scenario: Get empty crystals
    When get crystals
    Then response status code is 200
    And response body is "[]"

    # TODO write more tests
