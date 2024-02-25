# Books API Test Report

# Table of Contents

1. [Overview](#Overview)
2. [Test Objective](#Test-Objective)
3. [Test Execution Summary](#Testing-Approach)
4. [Defect Report](#Defect-Report)
5. [Approvals](#Approvals)

## Overview

This document is created to provide the details of testing activities to be performed on
CRUD Books API v1. 
## Test Objective

The main objectives is to ensure that the implementation is working as specified according to the requirements specification.

### Scope

#### In scope

Following Books API endpoints are covered as per Requirements Traceability Matrix:

| Name                         | Method | Path               | Test Case |
|------------------------------|--------|--------------------|-----------|
| Read all books               | GET    | /api/v1/books      |           |
| Read a particular book by ID | GET    | /api/v1/books/{id} |           |
| Create a book                | POST   | /api/v1/books      |           |
| Update a book                | PUT    | /api/v1/books/{id} |           |
| Delete book                  | DELETE | /api/v1/books/{id} |           |

Please refer to Books API documentation for technical details.

#### Out of Scope

Nonfunctional requirements including performance, security, efficiency etc.

### System under Test
CRUD Books API v1

### Testing Approach
Functional tests via [Automated Testing Suite](../README.md).

## Test Execution Summary

Test Executed: 11\
Test Passed: 2 (18%)\
Test Failed: 9\
Executed on: Mac OS X, OpenJDK 64-Bit Server VM 18.0.1.1+2-6\
Details of [cucumber execution](cucumber-report/cucumber.html)\
[evidences](evidences) of each Request/Response.
## Defect Report
#### Defect #1
Defect Description: Internal Server Error after calling DELETE method.
* [ERROR_api-v1-books_DELETE_2024-02-25T20:26:08.176448Z.log](evidences%2FERROR_api-v1-books_DELETE_2024-02-25T20%3A26%3A08.176448Z.log)
* [ERROR_api-v1-books_DELETE_2024-02-25T20:26:12.377163Z.log](evidences%2FERROR_api-v1-books_DELETE_2024-02-25T20%3A26%3A12.377163Z.log)
* [ERROR_api-v1-books_DELETE_2024-02-25T20:26:20.328143Z.log](evidences%2FERROR_api-v1-books_DELETE_2024-02-25T20%3A26%3A20.328143Z.log)
* [ERROR_api-v1-books_DELETE_2024-02-25T20:26:23.151819Z.log](evidences%2FERROR_api-v1-books_DELETE_2024-02-25T20%3A26%3A23.151819Z.log)
* [ERROR_api-v1-books_DELETE_2024-02-25T20:26:27.175051Z.log](evidences%2FERROR_api-v1-books_DELETE_2024-02-25T20%3A26%3A27.175051Z.log)
* [ERROR_api-v1-books_DELETE_2024-02-25T20:26:33.463465Z.log](evidences%2FERROR_api-v1-books_DELETE_2024-02-25T20%3A26%3A33.463465Z.log)

Steps to reproduce: Rerun "Delete Book via REST CRUD service" (or any other test which creates object calls DELETE at the end) scenario or curl with above requests.\
Expected Result: book should be removed with success response.
#### Defect #2 
Defect Description: Book pages field value is not being updated after PUT method call.
* [api-v1-books_POST_2024-02-23T13:18:06.881563Z.log](evidences%2Fapi-v1-books_POST_2024-02-23T13%3A18%3A06.881563Z.log)
* [api-v1-books_PUT_2024-02-23T13:18:09.434666Z.log](evidences%2Fapi-v1-books_PUT_2024-02-23T13%3A18%3A09.434666Z.log)

Steps to reproduce: Rerun "Update Book via REST CRUD service" or curl with above data.\
Expected Result: pages field value should be updated after PUT call accordingly.

#### Defect #3 
Defect Description: Internal Server Error after calling POST method with a book which already exists (with id).
* [ERROR_api-v1-books_POST_2024-02-25T20:26:13.840926Z.log](evidences%2FERROR_api-v1-books_POST_2024-02-25T20%3A26%3A13.840926Z.log)

Steps to reproduce: Rerun "Create a book which already exists" scenario or curl with above data.

#### Defect #4
Defect Description: 'pages' field value differs from the POST Payload request. \
Field/property 'pages' differ:\
actual value  : 628L\
expected value: 626L
* [api-v1-books_POST_2024-02-25T20:26:32.739998Z.log](evidences%2Fapi-v1-books_POST_2024-02-25T20%3A26%3A32.739998Z.log)

#### Defect #5 
Defect Description: Wrong scale of price field. Note, this behaviour is nondeterministic.\
field/property 'price' differ: \
actual value  : 29.260000100000003 \
expected value: 29.26 
* [api-v1-books_POST_2024-02-25T20:26:19.642814Z.log](evidences%2Fapi-v1-books_POST_2024-02-25T20%3A26%3A19.642814Z.log)
Steps to reproduce: Rerun "Create a Book: Positive" scenario or curl with above data. 

#### Defect #6
Defect Description: Internal Server Error after calling POST with null values
* [ERROR_api-v1-books_POST_2024-02-25T20:26:13.840926Z.log](evidences%2FERROR_api-v1-books_POST_2024-02-25T20%3A26%3A13.840926Z.log)

Steps to reproduce: Rerun "Create a Book: empty_string" scenario or curl with above data.

#### Defect #7
Defect Description: DELETE response per documentation should return removed object JSON representation but actual response is "Book has been deleted successfully" plain text
#### Defect #8 
Defect Description: Negative numbers allowed for pages and price. It's not specified by documentation but no validation on those fields in API at the moment.


## Approvals
N/A

