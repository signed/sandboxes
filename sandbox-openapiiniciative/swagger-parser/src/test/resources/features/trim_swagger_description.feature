Feature: Trim the swagger description by removing unreferenced elements

  Scenario: Remove Tags that are not referenced in any Operation
    Given a swagger api description with a tag definition that is not referenced in an operation
    And a tag definition that is referenced in a path operation
    When the swagger api description is trimmed
    Then the referenced tag definition is still present
    But the not referenced tag definition is removed

  Scenario: Do not print an empty tag block to json if all tag definitions are removed
    Given a swagger api description with only unreferenced tag definitions
    When the swagger api description is trimmed
    Then there is no tag property in the resulting json

  Scenario: Do not remove model definitions that are referenced in a path
    Given a swagger api description where a path references a model definition
    When the swagger api description is trimmed
    Then the referenced model definition is still present

  Scenario: Do not remove model definitions that are referenced in a parameter definition that is referenced
    Given a swagger api description where only a parameter definition references a model definition
    And the parameter definition is referenced anywhere
    When the swagger api description is trimmed
    Then the referenced model definition is still present

  Scenario: Do not remove model definitions that is referenced in a parameter
    Given a swagger api description where a parameter references a model definition
    When the swagger api description is trimmed
    Then the referenced model definition is still present

  Scenario: Do not remove model definitions that are referenced in a response
    Given a swagger api description where a response references a model definition
    When the swagger api description is trimmed
    Then the referenced model definition is still present

  Scenario: Do not remove model definitions that are referenced in a response definition
    Given a swagger api description where a response definition references a model definition
    And the response definition is referenced anywhere
    When the swagger api description is trimmed
    Then the referenced model definition is still present

  Scenario: Remove Model definitions that are not referenced in the rest of the document
    Given a swagger api description with an unreferenced definition
    When the swagger api description is trimmed
    Then the unreferenced definition is removed

  Scenario: Also remove all model definitions that become unreferenced by removing a unreferenced model definition
    Given a swagger api description with a definition
    And this definition is only referenced by another unreferenced definition
    When the swagger api description is trimmed
    Then booth definitions are removed

  Scenario: Remove parameter definitions that are not referenced anywhere
    Given a swagger api description with a parameter definition that is not referenced anywhere
    When the swagger api description is trimmed
    Then the unreferenced parameter definition is removed

  Scenario: Keep parameter definitions that are referenced in any operation
    Given a swagger api description with a parameter definition
    And the parameter definition is referenced in any operation
    When the swagger api description is trimmed
    Then the referenced parameter definition is still present

  Scenario: Keep parameter definitions that are referenced in any path
    Given a swagger api description with a parameter definition
    And the parameter definition is referenced in any path
    When the swagger api description is trimmed
    Then the referenced parameter definition is still present