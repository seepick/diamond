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

    # TODO write crystal CRUD itests

  Scenario: Get crystals paginated
    Given the following crystals exists in the database
      | weight |
      | 1      |
      | 2      |
    When prepare to get crystals
    And with skip 0 and take 1
    And execute prepared request
    Then the response status code is 200
    # TODO assert for list size = 1 and list[0] weightInGram = 1
    And store response JSON "$.items[0].id" as "ID"
    And the response JSON body is
    """
    {
      "meta": { "skip": 0, "take": 1, "hasMore": true },
      "items": [ { "id": "$ID", "weightInGram": 1 } ]
    }
    """

  Scenario: Get crystals sorted
    Given the following crystals exists in the database
      | weight |
      | 1      |
      | 2      |
    When prepare to get crystals
    And with sort for "weightInGram" in direction desc
    And execute prepared request
    Then the response status code is 200
    And the response JSON "$.items[0].weightInGram" is 2
    And the response JSON "$.items[1].weightInGram" is 1
