Feature: Trim the swagger description by removing unreferenced parameter definitions

  Scenario: Remove parameter definitions that are not referenced anywhere
    Given a parameter definition that is not referenced anywhere
    When trimmed
    Then the unreferenced parameter definition is removed

  Scenario: Keep parameter definitions that are referenced in any operation
    Given a parameter definition that is referenced in any operation
    When trimmed
    Then the referenced parameter definition is still present

  Scenario: Keep parameter definitions that are referenced in any path
    Given a parameter definition that is referenced in any path
    When trimmed
    Then the referenced parameter definition is still present
