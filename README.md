# 🌟 Adamur User Management System 🌟

## 📖 Project Overview

Welcome to the **Adamur User Management System**! This backend service provides a comprehensive solution for managing user accounts, including features such as registration, login, password resets, and email verification via OTP. Built using **Java Spring Boot**, **PostgreSQL**, **Docker**, and **GraphQL**, this system is designed with scalability and security in mind.

---

## 🚀 Getting Started

### Prerequisites

1. **Docker**: Ensure you have Docker installed on your machine. [Download Docker](https://www.docker.com/get-started).
2. **Environment Variables**: You'll need to configure several environment variables to run the application. 

### Step 1: Clone the Project

Clone this repository to your local machine using Git:

```bash
git clone https://github.com/your-username/adamus-user-management.git
cd getting-started-challenge
git checkout millatevin
```
### Step 2: Configure Environment Variables

```bash
# JWT Configuration
JWT_SECRET=<Your_JWT_Secret_Key> # Generate from https://jwtsecret.com/generate
JWT_EXPIRATION=3600000 # Token expiration time in milliseconds
JWT_REFRESH=86400000 # Refresh token expiration time in milliseconds

# Email Configuration (using Gmail)
SPRING_MAIL_USERNAME=<your_email@gmail.com>
SPRING_MAIL_PASSWORD=<your_app_password> # Create this using the steps here: https://knowledge.workspace.google.com/kb/how-to-create-app-passwords-000009237

# Database Configuration
POSTGRES_DB=<your_database_name>
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_db:5432/${POSTGRES_DB}
SPRING_DATASOURCE_USERNAME=<your_database_username>
SPRING_DATASOURCE_PASSWORD=<your_database_password>
```

### Step 3: Sping Up the Docker environment

```bash
docker compose -f dev-compose.yaml up
```

### Step 4: Access the GraphQL Playground

Copy: [GraphQL Interface](http://localhost:8080/graphiql?path=/graphql) 

##  🌍 User Journey

### 1. Register a new user 
-   Execute the follwoing mutation to register a new user
  
    ```bash
      mutation {
        registerUser(inputDTO: {
          email: "your_email@example.com",
          password: "password123"
        }) {
          id
          email
          isVerified
          createdAt
        }
      }
    ```
  -   Expected Response
    
      ```bash
          {
          "data": {
            "registerUser": {
              "id": "5",
              "email": "your_email@example.com",
              "isVerified": false,
              "createdAt": "Mon Oct 14 13:10:09 GMT 2024"
            }
          }
        }
    
### 2. Verify your Email
-   After registration, the app generates an OTP and sends it to your email. Verify the OTP with this mutation: (Replace the OTP with the actual OTP in your email)
  
    ```bash
          mutation {
            verifyOTP(email: "your_email@example.com", otp: "123456") {
              success
              message
            }
          }

    ```

 -   Expected Response
    
      ```bash
          {
          "data": {
            "verifyOTP": {
              "success": true,
              "message": "User status updated successfully"
            }
          }
        }
      
### 3. User Login
-   To log in, use the following mutation: (Use the password you set of your choice)
  
    ```bash
          mutation {
            login(input: {email: "your_email@example.com", password: "password123"}) {
              token
              refreshToken
              expiresIn
            }
          }
    ```

 -   Expected Response
    
      ```bash
           {
            "data": {
              "login": {
                "token": "<JWT_Token>",
                "refreshToken": "<Refresh_Token>",
                "expiresIn": 3599999
              }
            }
          }

### 4. Request Password Reset
-   If you forgot your password, you can request a password reset using your email. Copy this mutation
  
    ```bash
          mutation {
            requestPasswordReset(input: {email: "your_email@example.com"}) {
              success
              message
            }
          }
    ```

 -   Expected Response
    
      ```bash
           {
            "data": {
              "requestPasswordReset": {
                "success": true,
                "message": "Password reset email sent successfully"
              }
            }
          }

### 5. Password Reset
-   You can now reset your password with the reset token that has been sent to your email. Apply the mutation below to see for yourself: 
  
    ```bash
          mutation {
            resetPassword(input: {token: "your_reset_token", newPassword: "new_password123"}) {
              success
              message
            }
          }
    ```

 -   Expected Response
    
      ```bash
             {
            "data": {
              "resetPassword": {
                "success": true,
                "message": "Password reset successfully"
              }
            }
          }


 -   Error Response in case you have an Invalid or Expired token
    
      ```bash
           {
          "data": {
            "resetPassword": {
              "success": false,
              "message": "Invalid or expired password reset token"
            }
          }
        }

### Project Structure

- **Entity**: Contains the data models.
- **Controller**: Handles GraphQL mutations and queries.
- **Service and ServiceImpl**: Business logic related to user management.
- **Repositories**: Interfaces for database operations.
- **Config**: Additional details that power the Email, GraphQL and Security features
- **dto.user**: Responsible for communicating with the entities and crafting the API responses
- **Util**: Standard methods that power the JWT logic and OTP feature.

## 🌱 Contribution Guidelines

1. **Fork the Repository**: Create your branch for any changes you wish to make.
2. **Make Your Changes**: Ensure your code is clean and modular.
3. **Commit Your Changes**: Provide meaningful commit messages.
4. **Push to Your Fork**: Push your changes and create a pull request.





