This application is a backend implementation for a library system. It is built with the assumptions that:
1. There is an admin/Librarian that uses this application on behalf of users. (user registration, books storage and book loan data recordings)
2. With the first assumption, This application for simplicity was not implemented with security

## Build System
This application has been built with Java 21, utilizing the latest long term JDK version, Spring Boot 3.3.2 as the web framework, Maven for project management
The following dependencies was used to build this project"
1. Spring web for REST APIs
2. Spring data JPA for Object relational Mapping to the data base
3. H2 in-memory data base for data storage
4. Flyway migration for database migration, database consistency and versioning where it arises

To use, this application, Fork/Clone/Download this repo and launch it by entering http://localhost:8000/api/v1/books
The documentation for this application can be found here: [click here](https://documenter.getpostman.com/view/26545928/2sA3rzJC92)

Error handling has been implemented for this application such that requests that will lead to errors are handled by returning a 400 bad request Status code.
For user id, book id or bookLoan id that are not valid, a proper error message are accompanied with the 400 status code.
