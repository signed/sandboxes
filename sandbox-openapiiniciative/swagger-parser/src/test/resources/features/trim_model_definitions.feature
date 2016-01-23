Feature: Trim the swagger description by removing unreferenced model definitions

  Scenario: Do not remove model definitions that are referenced in a path
    Given a model definition that is referenced in a path
    When trimmed
    Then the model definition is still present

  Scenario: Do not remove model definitions that are referenced in a parameter definition that is referenced
    Given a model definition that is only referenced in a parameter definition
    And the parameter definition is referenced anywhere
    When trimmed
    Then the model definition is still present

  Scenario: Do not remove a model definition that is referenced in a parameter
    Given a model definition referenced by a parameter
    When trimmed
    Then the model definition is still present

  Scenario: Do not remove model definitions that are referenced in a response
    Given a model definition that is referenced in a response
    When trimmed
    Then the model definition is still present

  Scenario: Do not remove model definitions that are referenced in a response definition
    Given a model definition that is referenced in a response definition
    And the response definition is referenced anywhere
    When trimmed
    Then the model definition is still present

  Scenario: Remove Model definitions that are not referenced in the rest of the document
    Given an unreferenced model definition
    When trimmed
    Then the model definition is removed

  Scenario: Also remove all model definitions that become unreferenced by removing a unreferenced model definition
    Given a model definition that is only referenced by another model definition that is not referenced anywhere
    When trimmed
    Then booth definitions are removed