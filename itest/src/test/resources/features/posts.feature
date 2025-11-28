Feature: Posts endpoint

  Scenario: Get empty posts
    When get posts
    Then response status code is 200
    And response posts are empty

  Scenario: Get posts
    Given the following posts are returned by the backend
      | id | user id | title | completed |
      | 1  | 11      | foo   | false     |
      | 2  | 22      | bar   |           |
    When get posts
    Then response status code is 200
    And response posts are
      | id | title |
      | 1  | foo   |
      | 2  | bar   |
