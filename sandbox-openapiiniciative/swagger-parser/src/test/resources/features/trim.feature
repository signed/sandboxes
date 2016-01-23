Feature: Trim the swagger description by removing unreferenced elements

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

  Scenario: Do not remove model definitions that are referenced in a path
    Given a model definition that is referenced in a path
    When trimmed
    Then the referenced model definition is still present

  Scenario: Do not remove model definitions that are referenced in a parameter definition that is referenced
    Given only a parameter definition references a model definition
    And the parameter definition is referenced anywhere
    When trimmed
    Then the referenced model definition is still present

  Scenario: Do not remove model definitions that is referenced in a parameter
    Given a parameter references a model definition
    When trimmed
    Then the referenced model definition is still present

  Scenario: Do not remove model definitions that are referenced in a response
    Given a response references a model definition
    When trimmed
    Then the referenced model definition is still present

  Scenario: Do not remove model definitions that are referenced in a response definition
    Given a response definition references a model definition
    And the response definition is referenced anywhere
    When trimmed
    Then the referenced model definition is still present

  Scenario: Remove Model definitions that are not referenced in the rest of the document
    Given an unreferenced definition
    When trimmed
    Then the unreferenced definition is removed

  Scenario: Also remove all model definitions that become unreferenced by removing a unreferenced model definition
    Given a definition
    And this definition is only referenced by another unreferenced definition
    When trimmed
    Then booth definitions are removed

  Scenario: Remove parameter definitions that are not referenced anywhere
    Given a parameter definition that is not referenced anywhere
    When trimmed
    Then the unreferenced parameter definition is removed

  Scenario: Keep parameter definitions that are referenced in any operation
    Given a parameter definition
    And the parameter definition is referenced in any operation
    When trimmed
    Then the referenced parameter definition is still present

  Scenario: Keep parameter definitions that are referenced in any path
    Given a parameter definition
    And the parameter definition is referenced in any path
    When trimmed
    Then the referenced parameter definition is still present

  Scenario: Remove response definitions that are not referenced anywhere
    Given a response definition that is not referenced anywhere
    When trimmed
    Then the unreferenced response definition is removed

  Scenario: Keep response definitions that are referenced in any operation
    Given a response definition
    And the response definition is referenced in any operation
    When trimmed
    Then the referenced response definition is still present
