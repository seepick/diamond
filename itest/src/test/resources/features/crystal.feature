Feature: Crystal endpoint

  Scenario: Get crystals when empty
    When get crystals
    Then the response status code is 200
    And the response is an empty page

  Scenario: Get crystals when single exists
    Given the following crystals exists in the database
      | weight |
      | 42     |
    When get crystals
    Then the response status code is 200
    And the response JSON "$.items[0].weightInGram" is 42

  Scenario: Get crystals when single exists
    Given the following crystals exists in the database
      | weight |
      | 1      |
      | 2      |
    When prepare to get crystals
    And with skip 0 and take 1
    And execute prepared request
    Then the response status code is 200
    And store response JSON "$.items[0].id" as "ID"
    And the response JSON body is
    """
    {
      "meta": { "skip": 0, "take": 1, "hasMore": true },
      "items": [ { "id": "$ID", "weightInGram": 1 } ]
    }
    """

    # TODO write CRUD tests
