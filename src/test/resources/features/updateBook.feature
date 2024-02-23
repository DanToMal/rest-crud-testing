Feature: Test Books Api

  Background:
    Given Client is able to access Books API

  Scenario: Update a Book properties
    When Following Book is created
      | name              | author        | publication | category | pages | price |
      | Europe: A History | Norman Davies | Amazon      | History  | 626   | 23    |
    And Read a particular book by ID response contains created book with "Europe: A History" name
    And Read all books response contains created books
    Then Created book with "Europe: A History" name is updated as follows
      | name         | author         | publication         | category      | pages | price |
      | Updated name | Updated Author | Updated publication | Updated pages | 625   | 25.50 |
    And Read a particular book by ID response contains created book with "Updated name" name
    And Read all books response contains created books

  Scenario: Create a new Book by updating non existing resource
    When Following Book with non existing ID is updated
      | name                                                                      | author            | publication           | category | pages | price  |
      | The Strategy of Denial American Defense in an Age of Great Power Conflict | Elbridge A. Colby | Yale University Press | Military | 1500  | 330.03 |
    Then Read a particular book by ID response contains created book with "Updated name" name
    Then Read all books response contains created books