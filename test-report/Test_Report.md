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
Test Passed: 6\
Test Failed: 5\
Executed on: Mac OS X, OpenJDK 64-Bit Server VM 18.0.1.1+2-6\
Please find details of each execution under [Cucumber-report](cucumber-report/cucumber.html)
## Defect Report
Defect #1 \
Defect Description: Book pages field value is not being updated after PUT method call.
* [_api-v1-books_POST_2024-02-23T13:18:06.881563Z.log](evidences%2F_api-v1-books_POST_2024-02-23T13%3A18%3A06.881563Z.log)
* [_api-v1-books_PUT_2024-02-23T13:18:09.434666Z.log](evidences%2F_api-v1-books_PUT_2024-02-23T13%3A18%3A09.434666Z.log)\
Steps to reproduce: Rerun "Update Book via REST CRUD service" or curl with above data.\
Expected Result: pages field value should be updated after PUT call accordingly.

Defect #2 \
Defect Description: Internal Server Error after calling POST method with a book which already exists (with id). \
* [_api-v1-books_POST_2024-02-23T13:18:06.881563Z.log](evidences%2F_api-v1-books_POST_2024-02-23T13%3A18%3A06.881563Z.log)\
Steps to reproduce: Rerun "Create a book which already exists" scenario or curl with above data. \

Defect #3 \
Defect Description: Internal Server Error after calling DELETE method. \
* [ERROR__api-v1-books_DELETE_2024-02-23T13:05:12.217253Z.log](evidences%2FERROR__api-v1-books_DELETE_2024-02-23T13%3A05%3A12.217253Z.log)
* [ERROR__api-v1-books_DELETE_2024-02-23T13:05:20.223364Z.log](evidences%2FERROR__api-v1-books_DELETE_2024-02-23T13%3A05%3A20.223364Z.log)
* [ERROR__api-v1-books_DELETE_2024-02-23T13:17:52.242677Z.log](evidences%2FERROR__api-v1-books_DELETE_2024-02-23T13%3A17%3A52.242677Z.log)
* [ERROR__api-v1-books_DELETE_2024-02-23T13:18:05.243210Z.log](evidences%2FERROR__api-v1-books_DELETE_2024-02-23T13%3A18%3A05.243210Z.log) \
Steps to reproduce: Rerun "Delete Book via REST CRUD service" scenario or curl with above data. \

Defect #4 \
Defect Description: Wrong scale of price field. Note, this behaviour is nondeterministic.\
field/property 'price' differ: \
actual value  : 29.260000100000003 \
expected value: 29.26 
* [_api-v1-books_POST_2024-02-23T13:17:33.763706Z.log](evidences%2F_api-v1-books_POST_2024-02-23T13%3A17%3A33.763706Z.log) \
Steps to reproduce: Rerun "Create a Book: Positive" scenario or curl with above data. \

## Approvals
N/A

