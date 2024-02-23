@delete
Feature: Delete Book via REST CRUD service

  Scenario Outline: Delete a book: <scenario>
    Given Client is able to access Books API
    And Following Book is created
      | name   | author   | publication   | category   | pages   | price   |
      | <name> | <author> | <publication> | <category> | <pages> | <price> |
    And Read a particular book by ID response contains created book with "<name>" name
    When Book with "<name>" name is deleted
    Then Deleted response content is "Book has been deleted successfully"
    And Read a particular book by ID response does not contain deleted book with "<name>" name
    And Read all books response does not contain deleted books

    Examples:
      | scenario            | name                                | author                                         | publication                 | category    | pages | price |
      | Price_decimal_point | Test Driven Development: By Example | Kent Beck                                      | Addison-Wesley Professional | Programming | 240   | 29.26 |
      | Price_integer       | Fairy Tale                          | Stephen King                                   | None                        | Fantasy     | 599   | 15    |
      | Multiple_authors    | Study of Mediterranean History      | H. J. S. Finch, A. M. Samuel and G. P. F. Lane | Oxford                      | Historic    | 222   | 8000  |