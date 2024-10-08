# Adamur Entry Test - User Management System

## Project Description

In this test, you will build a backend user management system with the following features:
- **User Registration and Validation**
- **Secure Login with JWT Tokens**
- **Email-Based Account Verification (using OTP)**
- **Password Reset Functionality**

The system is built using **GraphQL** and **Prisma** for database management. 

The goal is to develop a scalable, secure, and adaptable solution that could easily integrate into an educational platform like Adamur, which helps African developers master tech skills.


## Requirements & Features

### 1. **User Registration**
- Create a GraphQL mutation for user registration using email and password.
- Validate email format and ensure password strength (minimum length, alphanumeric).
- Encrypt passwords with `bcryptjs` before storing them in the database.
- Send a **One-Time Password (OTP)** via email for account verification.

### 2. **User Login**
- Create a GraphQL mutation for user login.
- Authenticate users by verifying their email and password.
- Return a **JWT token** for successful logins.
- Gracefully handle incorrect login attempts and notify non-verified users.

### 3. **Account Verification**
- After registration, send an OTP to the user's email address.
- Implement a GraphQL mutation to verify the account using the OTP and mark the user's account as verified.

### 4. **Password Reset**
- Implement a GraphQL mutation to request a password reset.
- Send a password reset link with a secure token to the user's email.
- Create a mutation to reset the password using the token.

## Unique to Adamur

- **Local Relevance**: Ensure that the system is designed to fit use cases relevant to African developers and tech platforms.
- **Build as You Learn**: Document the process thoroughly, reflecting on key decisions.
- **Security & Scalability**: Demonstrate how the system is secure (e.g., encryption, token management) and scalable for future growth.

## Technology Stack

- **Backend**: Node.js (Express, GraphQL)
- **Database**: Prisma (PostgreSQL or SQLite)
- **Email Service**: Nodemailer (Gmail or other SMTP)
- **Authentication**: JWT (for login and password reset)

## Best Practices & Guidelines

1. **Code Structure**: Organize the codebase into modules (models, resolvers, services). Ensure each component is logically separated and easy to maintain.
2. **Environment Variables**: Use `dotenv` for managing sensitive information (e.g., database connection, JWT secret).
3. **Error Handling**: Provide user-friendly error messages and avoid exposing unnecessary details (e.g., stack traces).
4. **Testing**: Write unit and integration tests for all major functionalities.

## Git Version Control

- Use feature branches for significant functionalities.
- Commit frequently with meaningful messages.
- Maintain a public GitHub repository with a clear commit history and structured codebase.

## Documentation

Please provide a README with:
1. **Project Structure Overview**
2. **Setup Instructions** (e.g., how to run the project locally)
3. **Summary of GraphQL Endpoints** (with examples)
4. **Testing Instructions**

## Evaluation Criteria

1. **Code Quality**: Ensure clean, modular, and maintainable code.
2. **Correctness**: The solution should meet all functional requirements.
3. **Product Thinking**: Consider how the system can be adapted or scaled for future growth.
4. **Documentation & Testing**: Include clear documentation, setup instructions, and thorough test coverage.
5. **Commit Frequency**: Use a well-documented Git history that shows progress.

## Bonus Points

- Use **TypeScript** for type safety and a clearer code structure.
- Implement **Role-Based Access Control (RBAC)** for different user types (e.g., Admin, User).
- Extend the system to support **Web3** or **Blockchain-Based Identity Verification**.

## Submission

Submit the link to your GitHub repository by the **10th 2024**. Ensure that the code is well-structured, follows best practices, and includes tests.
