# Getting Started

To get started with running tests against Books Rest API, follow these steps:

1. Clone the repository: `git clone https://github.com/DanToMal/rest-crud-testing.git`
2. Configure the REST API connection details, by including user credentials in the test configuration file (*src/test/resources/application.yml*):
    ```
   books-api:
     ...
     username: ${books.username}
     password: ${books.password}
    ``` 
and then run Cucumber tests from maven command: `mvn clean test`, or pass the user credentials via corresponding command line arguments directly
   ```
   mvn clean test -Dbooks.username=XXX -Dbooks.password=YYY
   ```
## Local Development
To run tests against local standalone WireMock server instance run following

```
mvn clean test -Dspring.profiles.active=local
```

## Reference Documentation

For further reference on test libraries used in project, please consider the following sections:

* [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
* [REST Assured Usage](https://github.com/rest-assured/rest-assured/wiki/Usage)
* [WireMock User Documentation](https://wiremock.org/docs/)
