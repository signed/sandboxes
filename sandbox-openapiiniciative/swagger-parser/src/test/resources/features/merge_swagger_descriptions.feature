Feature: Merge two separate api definitions into one

  Scenario: Merge two distinct api definitions
    Given two distinct swagger api descriptions
    When the two are merged
    Then the path elements of booth are in the resulting swagger api description
