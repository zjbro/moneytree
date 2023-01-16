## VTTP Project: Money Tree

An application developed as part of the requirements for NUS-ISS Profession Diploma in Software Development.

### Built With

* [![Angular][Angular.io]][Angular-url]
* [![Springboot][Spring.io/projects/spring-boot]][Springboot-url]

<!-- ABOUT -->
## ABOUT

### Register/Login

Users are able to register for an account and subsequently login with the same credentials.
Authentication is done with JWT.

### Add/Edit/Remove 

Once logged in, users are able to add a new expense or view their expense history.
When adding a new expense, the following fields are available:
* Category (Required)
* Description
* Date (If left blank, the current date will automatically be filled in)
* Amount (Required)
* Image file upload

Information of past expenses are displayed in a table format.
Users have the option to edit or delete any entries.
The table is supported with pagination and search function from Material Angular.



