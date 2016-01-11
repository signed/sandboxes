Feature: Remove operations that are not marked
  The idea is to tag the path definitions that should be retained.
  After the merge all paths that do not have the discriminating tag are removed.
  The tag is removed from the remaining path definitions.


  Scenario: Remove all path definitions that do not have the tag
    Given a merged swagger api description
    And there is a path definition without the tag
    And there is a path definition with the tag
    When the swagger api description gets cleaned
    Then the untagged path definition is removed
    And the tagged path definition is still present
    But the tag is removed
