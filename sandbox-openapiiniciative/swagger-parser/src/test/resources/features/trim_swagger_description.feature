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

#  Scenario: Remove Model definitions that are not referenced in the rest of the document
#  Scenario: A Model definition can reference another model definition.
# Ensure that if a single reference gets removed by trim the than orphaned definition gets removed as well