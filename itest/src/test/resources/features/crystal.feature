Feature: Crystal endpoint

  Scenario: Get crystals when empty
    When get crystals
    Then the response status code is 200
    And the response is an empty page

  Scenario: Get crystals when single exists
    Given the following crystals exists in the database
      | weight |
      | 11     |
    When get crystals
    Then the response JSON "$.items[0].weightInGram" is 11

    # TODO write CRUD tests
