Feature: Merge two separate api definitions into one

  Scenario: Merge two distinct api definitions
    Given two distinct swagger api descriptions
    When the two are merged
    Then the path elements of booth are in the resulting swagger api description

  @debug
  Scenario: If booth swagger api definitions contain an identical api definition drop one
    Given two swagger api definitions with two identical path definitions
    When the two are merged
    Then the path definition is contained only once

  Scenario: Include the model definitions from both sagger api descriptions
    Given two swagger api description with distinct model definitions
    When the two are merged
    Then the model definitions of booth are in the resulting swagger api description

  Scenario: Only include on of the definition if they are defined in booth
    Given two swagger api descriptions that contain two identical definitions
    When the two are merged
    Then the definition is contained only once

  Scenario: Inform about conflicting definitions
    Given two swagger api descriptions that have conflicting definitions
    When the two are merged
    Then the caller is informed about the conflict