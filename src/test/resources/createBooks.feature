Feature: Test Books Api

  Scenario: Create Book
    Given Following Book is created
      | name                                | author    | publication                 | category    | pages | price |
      | Test Driven Development: By Example | Kent Beck | Addison-Wesley Professional | Programming | 240   | 29.26 |
    Then Response status code is 200
