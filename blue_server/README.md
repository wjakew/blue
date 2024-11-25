# Blue Server

**by Jakub Wawak**  
**Email:** kubawawak@gmail.com  
**All rights reserved**

## Description

Blue Server is a server application designed for storing blue notes. It provides a secure way to manage user tokens and integrates with Spring Security for authentication. The application uses JWT (JSON Web Tokens) for secure token generation and management.

## Features

- tba - still in development

## REST Endpoints

This section documents the available REST endpoints for user account management in the application.

### User Account Endpoints

#### Create User Account

- **Endpoint:** `/api/users/create`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
      "email": "user@example.com",
      "password": "yourPassword",
      "telephone": "123456789"
  }
  ```
- **Response:**
  - **Success:**
    ```json
    {
        "status": "Success",
        "message": "User account created successfully."
    }
    ```
  - **Error:**
    ```json
    {
        "status": "Error",
        "message": "Email, password, and telephone are required."
    }
    ```

#### Disable User Account

- **Endpoint:** `/api/users/disable`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
      "email": "user@example.com"
  }
  ```
- **Response:**
  - **Success:**
    ```json
    {
        "status": "Success",
        "message": "User account disabled successfully."
    }
    ```
  - **Error:**
    ```json
    {
        "status": "Error",
        "message": "Email is required."
    }
    ```

### Notes
- Ensure that the email provided for creating or disabling a user account is unique and valid.
- Passwords should be securely hashed before storage.

## Technologies Used

- Java
- Spring Boot
- Vaadin
- MongoDB (for database operations)
- JWT (JSON Web Tokens)

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/blue_server.git
   cd blue_server
   ```

2. **Set up your environment:**
   - Ensure you have Java 11 or higher installed.
   - Set up a MongoDB instance and configure the connection string in the `blue.properties` file.

3. **Build the project:**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

## Configuration

The application uses a properties file (`blue.properties`) for configuration. Below are the required properties:


### Important Notes

- Ensure that `blueSecret` is a valid 256-bit key for JWT signing.
- The `tokenExpirationTime` defines how long the generated tokens are valid.

## Usage

- The application starts by loading the properties from `blue.properties`.
- It checks for the presence of required properties (`blueSecret` and `tokenExpiration`).
- If the properties are valid, the application will run, and you can interact with it through the defined endpoints.

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue for any suggestions or improvements.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries, please contact Jakub Wawak at kubawawak@gmail.com.