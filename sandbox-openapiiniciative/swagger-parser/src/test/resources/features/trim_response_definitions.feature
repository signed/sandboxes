Feature: Trim the swagger description by removing unreferenced elements

  Scenario: Remove response definitions that are not referenced anywhere
    Given a response definition that is not referenced anywhere
    When trimmed
    Then the unreferenced response definition is removed

  Scenario: Keep response definitions that are referenced in any operation
    Given a response definition that is referenced in any operation
    When trimmed
    Then the referenced response definition is still present
