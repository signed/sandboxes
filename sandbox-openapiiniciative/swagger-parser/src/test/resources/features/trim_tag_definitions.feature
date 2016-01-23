Feature: Trim the swagger description by removing unreferenced tag definitions

  Scenario: Remove Tags that are not referenced in any Operation
    Given a tag definition that is not referenced in an operation
    And a tag definition that is referenced in a path operation
    When trimmed
    Then the referenced tag definition is still present
    But the not referenced tag definition is removed

  Scenario: Do not print an empty tag block to json if all tag definitions are removed
    Given only unreferenced tag definitions
    When trimmed
    Then there is no tag property in the resulting json
