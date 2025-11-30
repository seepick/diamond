Feature: check the home for greeting

  Background:
    * url baseUrl
    * def endpointBase = '/'

  Scenario: request home succeeds
    Given path endpointBase
    When method GET
    Then status 200
    And match response == 'Hello Service!'
