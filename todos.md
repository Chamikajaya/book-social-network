### TODOs:

**Refactor the functions to smaller chunks**

* Integrate flyway for database migration 😊

* check how to reduce code duplication for updating shareable & archived status
* Fix duplicate books add - using ISBN
* **Rethink about getAllBorrowedBooksOfUser & getAllReturnedBooksOfUser**


* Add a custom exception to handle validation errors
* Try to utilize inheritance to reduce code duplication - add a BaseEntity with common fields :)

### Deployment:

* Change OpenAPIConfig class specs
* MailDev docker compose change local-host
* build the image + CI/CD pipeline