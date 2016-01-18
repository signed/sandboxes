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

  Scenario: Remove Model definitions that are not referenced in the rest of the document
    Given a swagger api description with an unreferenced definition
    When the swagger api description is trimmed
    Then the unreferenced definition is removed

  Scenario: Also remove all model definitions that become unreferenced by removing a unreferenced model definition
    Given a swagger api description with a definition
    And this definition is only referenced by another unreferenced definition
    When the swagger api description is trimmed
    Then booth definitions are removed

  Scenario: Remove Parameter Definitions that are not referenced anywhere
    Given a swagger api description with a parameter definition that is not referenced anywhere
    When the swagger api description is trimmed
    Then the unreferenced parameter definition is removed