@create
Feature: Create Book via REST CRUD service

  Background:
    Given Client is able to access Books API

  Scenario Outline: Create a Book: <scenario>
    When Following Book is created
      | name   | author   | publication   | category   | pages   | price   |
      | <name> | <author> | <publication> | <category> | <pages> | <price> |
    And Read a particular book by ID response contains created book with "<name>" name
    Then Read all books response contains created books

    Examples:
      | scenario         | name                                                   | author           | publication                 | publication | pages | price  |
      | Positive         | Test Driven Development: By Example                    | Kent Beck        | Addison-Wesley Professional | Programming | 240   | 29.26  |
      | Positive2        | Clean Code: A Handbook of Agile Software Craftsmanship | Robert C. Martin | Prentice Hall               | Programming | 464   | 22     |
      | special_chars    | &quot;cool&quot;                                       | 'n'\t    ${}     | null                        | //s         | 999   | 100.01 |
      | empty_string     | Empty                                                  |                  |                             |             |       |        |
      | Negative_numbers | No content                                             | author           | publication                 | publication | -4    | -4     |

  Scenario: Create a book which already exists
    And Following Book is created
      | name                                | author    | publication                 | category    | pages | price |
      | Test Driven Development: By Example | Kent Beck | Addison-Wesley Professional | Programming | 999   | 29.26 |
    When Already created book is created again
#  todo expected behaviour is not specified
