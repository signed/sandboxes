Feature: Merge two separate api definitions into one

  Scenario: Merge two distinct api definitions
    Given two distinct swagger api descriptions
    When merged
    Then the path elements of booth are in the resulting swagger api description
    And the tag definitions of booth are in the resulting swagger api description

  Scenario: If booth swagger api definitions contain an identical api definition drop one
    Given two swagger api definitions with two identical path definitions
    When merged
    Then the path definition is contained only once

  Scenario: Report if there are conflicting path definitions
    Given two swagger api definitions with conflicting path definitions
    When merged
    Then the caller is informed about the conflict

  Scenario: Include the model definitions from both sagger api descriptions
    Given two swagger api description with distinct model definitions
    When merged
    Then the model definitions of booth are in the resulting swagger api description

  Scenario: Only include one of the definition if they are defined in booth
    Given two swagger api descriptions that contain two identical definitions
    When merged
    Then the definition is contained only once

  Scenario: Inform about conflicting definitions
    Given two swagger api descriptions that have conflicting definitions
    When merged
    Then the caller is informed about the conflict

  Scenario: Only include one of the tag definitions if they are defined in booth
    Given two swagger api descriptions that contain two identical tag definitions
    When merged
    Then there is only a single tag definition

  Scenario: Detect if there are conflicting tag definitions
    Given two swagger api descriptions with conflicting tag definitions
    When  merged
    Then the caller is informed about the conflict