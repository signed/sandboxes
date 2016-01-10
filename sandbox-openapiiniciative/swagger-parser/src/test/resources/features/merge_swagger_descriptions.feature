Feature: Merge to separate swagger files into a single swagger api

  Scenario: Merge two distinct api definitions
    Given two distinct swagger api descriptions
    When the two are merged
    Then the path elements of booth are in the resulting swagger api description
