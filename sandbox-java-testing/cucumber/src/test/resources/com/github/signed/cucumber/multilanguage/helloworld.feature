# language: en
Feature: Hello World

  Scenario: Say hello
    Given I have a hello app with "Howdy"
    When I ask it to say hi
    Then it should answer with "Howdy World"

  Scenario: Say hello pirate style
    Given I have a hello app with "Arrr"
    When I ask it to say hi
    Then it should answer with "Arrr World"