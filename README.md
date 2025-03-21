 # Full Stack Chat Application with Spring Boot and React

This project demonstrates a full-stack chat application built using Spring Boot for the backend and React for the frontend. It leverages WebSockets for real-time communication and incorporates features like user authentication, workspace management, and a robust chat interface.

## Features

* **User Authentication:** Secure user registration and login functionality.
* **Workspace Management:** Users can create and manage their own workspaces.
* **Real-time Chat:**  Instant message delivery using WebSockets via STOMP.
* **Intuitive UI:**  A modern and user-friendly interface built with React.
* **Backend API:** RESTful API built with Spring Boot for handling data persistence and business logic.

## Technologies Used

* **Backend:**
    * Java 21
    * Spring Boot 3.4.2
    * Spring Security
    * Spring Data JPA
    * MapStruct
    * Lombok
    * Maven
    * Spotless (Code Formatting)
    * Modulith (Modular Architecture)
* **Frontend:**
    * React
    * JavaScript
    * STOMP (for WebSockets)
    * Axios or Fetch API (for HTTP requests)
* **Database:** (Not specified in provided code, likely a relational database like PostgreSQL or MySQL)
* **Other:**
    * LiteLLM (Integration suggested by test file, but not fully implemented in provided code)


## Getting Started

### Prerequisites

* Java Development Kit (JDK) 21
* Node.js and npm (or yarn)
* Maven
* Your preferred database (e.g., PostgreSQL, MySQL)

### Backend Setup

1. Navigate to the `backend/` directory.
2. Configure your database connection in the `application.properties` or `application.yml` file (this file was not provided, so you'll need to create it within `backend/src/main/resources`).
3. Run `mvn spring-boot:run` to start the backend application.

### Frontend Setup

1. Navigate to the `frontend/` directory.
2. Install dependencies using `npm install` or `yarn install`.
3. Update the API endpoint URL in your frontend code (e.g., in `api.js` or wherever API calls are made) to match the backend server address.
4. Run `npm start` or `yarn start` to start the frontend development server.

## Future Enhancements

* **Improved Error Handling:** More comprehensive error messages and handling.
* **Advanced Chat Features:** File sharing, notifications, user mentions, etc.
* **LiteLLM Integration:**  Complete the integration with LiteLLM to add AI capabilities to the chat.  The current code only includes a test for LiteLLM, but no actual implementation.  You will need to add dependencies and the necessary code.
* **Scalability and Performance Optimization:**  Address potential performance bottlenecks as the application grows.
* **Deployment:** Containerize the application using Docker and deploy to a cloud platform.


## License
MIT licence
