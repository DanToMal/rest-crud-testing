Feature: Test Books Api

  Scenario Outline: Create a Book: <scenario>
    Given Client is able to access Books API
    When Following Book is created
      | name   | author   | publication   | category   | pages   | price   |
      | <name> | <author> | <publication> | <category> | <pages> | <price> |
    Then Read all books response contains created books
    And Read a particular book by ID response contains created book with "Test Driven Development: By Example" name

    Examples:
      | scenario         | name                                | author    | publication                 | category    | pages | price |
      | Positive         | Test Driven Development: By Example | Kent Beck | Addison-Wesley Professional | Programming | 123   | 15    |
#      | special_chars    | &quot;cool&quot;                    | 'n'\t     | ${}                         | //s         | 999   | 29.26 |
#      | empty_string     |                                     |           |                             |             |       |       |
#      | decimal_pages    | Test Driven &quot;cool&quot;        | 'n'\t     | Addison-Wesley Professional | Programming | 0.5   | 29.26 |
#      | Negative_numbers | Test Driven Development: By Example | Kent Beck | Addison-Wesley Professional | Programming | -4    | -4    |

  Scenario: Create a book which already exists
    Given Client is able to access Books API
    And Following Book is created
      | name                                | author    | publication                 | category    | pages | price |
      | Test Driven Development: By Example | Kent Beck | Addison-Wesley Professional | Programming | 999   | 29.26 |
    When Already existing book is created
