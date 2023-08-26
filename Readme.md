<a href="https://www.alura.com.br/challenges/back-end-7/">
    <img src="https://ada-site-frontend.s3.sa-east-1.amazonaws.com/home/header-logo.svg" alt="Ada logo" title="Ada" align="left" height="80px"/>
</a>

<div align="center">
  
# :shopping_cart: Order API 

![Docker](https://img.shields.io/badge/-Docker-000?&logo=Docker)
![Linux](https://img.shields.io/badge/-Linux-000?&logo=Linux)
![Spring](https://img.shields.io/badge/-Spring-000?&logo=Spring)
![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-000?&logo=Postgresql)
![Git](https://img.shields.io/badge/Git-000?&logo=Git)
![VSCode](https://img.shields.io/badge/VSCode-000?&logo=visualstudiocode)

This repository contains the source code for OrderAPI, a backend API for managing orders and product stock. The API allows customers to browse available products and place orders. Once an order is placed, confirmation emails are sent to both the customer and the inventory team.

</div>


## Features

- User registration: User can register with username and password
- Costumer registration: Authenticated users can create a costumer profile with their name, cpf, email, and address.
- List available products.
- Place a order with stock validation and update of stock quantity.
- Sending a confirmation email to the customer.
- Sending an email to the sales department responsible.
- Admin user can retrieve and saving products from an external API: [https://dummyjson.com/products/search?q=phone](https://dummyjson.com/products/search?q=phone), list users and customers details.

## Technologies Used

- Spring Security: Used for user authentication and authorization.
- JSON Web Tokens (JWT): Used for securing APIs and providing authorization tokens.
- Spring Data JPA: Used for database access and object-relational mapping.
- Spring Boot: Used for creating standalone Spring-based applications.
- Swagger/OpenAPI: Used for API documentation and testing.
- Lombok: Used for generating boilerplate code, reducing boilerplate code in the classes.
- Sendgrid: Used for email service


## Local Development

To develop the project locally, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/ecureuill/orderapi.git
   ```

1. Make sure you have Java JDK and Maven installed on your machine.

1. Configure the PostgreSQL database connection in the `application.properties` file:

   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/orderapi
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
1. Add SENDGRID_API_KEY environment variable ([create a sendgrid account and generate your secret](https://docs.sendgrid.com/pt-br/for-developers/sending-email/api-getting-started))
    ```bash
        export SENDGRID_API_KEY='YOUR_SECRET'
    ```
1. Build and run the application using Maven:

   ```
   mvn spring-boot:run
   ```

6. The application should now be running locally on [http://localhost:8080](http://localhost:8080).