**Library Management System**
The Library Management System is a Spring Boot–based application designed to manage libraries, aisles, and books efficiently.
It demonstrates modern backend design principles including REST APIs, JPA relationships (@ManyToMany, @JsonIgnore, @JsonManagedReference / @JsonBackReference), and modular architecture.

**Tech Stack**

Java 17
Spring Boot 3.x
Spring Data JPA / Hibernate
RESTful APIs
Maven
H2 / MySQL (configurable)
Lombok
Git & GitHub

Manage Libraries, Aisles, and Books
Handles bi-directional relationships cleanly using @JsonManagedReference / @JsonBackReference
Supports CRUD operations
Uses cascade persistence and orphan removal for clean data management
REST endpoints for seamless integration with frontend or third-party systems

# Clone the repository
git clone https://github.com/gattemvijay/LibraryManagement.git

# Navigate into the project directory
cd LibraryManagement

# Build the project
mvn clean install

# Run the Spring Boot app
mvn spring-boot:run

Example Endpoints
Entity	Method	   Endpoint	                                     Description
Library	 POST	   /api/libraries	                            Create a new library
Aisle	   GET	   /central/aisles	                          get all aisles in a library
Library	 PUT	   /api/libraries/1	 	                        Update existing library
Book	   GET	   /api/books                                 Get all books
Book     GET     central/natural-history/books              get all books based on isle name 'NATURAL HISTORY' in the library name 'CENTRAL LIBRARY'
