

# Spring Boot WebSocket Chat Backend

This is a real-time chat backend built with Spring Boot, STOMP over WebSocket, and SockJS. It supports multiple users, tracks online users, and broadcasts messages to all connected clients.

## Features

* **Real-time Chat**: Uses WebSocket and STOMP for real-time communication.
* **User Tracking**: Tracks online users and broadcasts join/leave events.
* **Message Broadcasting**: Sends chat messages to all connected clients.
* **CORS Support**: Allows frontend applications (e.g., Angular, GitHub Pages) to connect securely.

## WebSocket Endpoints

* **WebSocket handshake**: `/ws` (with SockJS fallback)
* **Send message**: `/app/chat.sendMessage`
* **Add user**: `/app/chat.addUser`
* **Broadcast topic**: `/topic/public`

## How It Works

1. **Client Connects**: The client connects to the `/ws` endpoint and subscribes to `/topic/public`.
2. **User Joins**: The user sends a `JOIN` message to `/app/chat.addUser`, which is broadcast to all clients.
3. **Send Message**: The user sends a `CHAT` message to `/app/chat.sendMessage`, which is broadcast to all connected clients.
4. **User Leaves**: When a user disconnects, a `LEAVE` message is broadcast and the online user count is updated.

## Deployment

This backend includes a bundled frontend (HTML, CSS, JS). When you run the backend, static resources (like `index.html`) will be served from the `src/main/resources/static` directory. You can access the chat application at [http://localhost:8080](http://localhost:8080).

### External Frontends

You can also connect external frontends (such as Angular or a static site hosted elsewhere) to the backend via WebSocket. A separate Angular frontend is available at:
[https://github.com/thughari/Socket-UI](https://github.com/thughari/Socket-UI).

## Configuration

In the `src/main/java/com/thughari/chat/config/WebSocketConfig.java` file, you can configure the WebSocket endpoints and allowed origins for security. Here’s an example:

```java
@Override
public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
            .setAllowedOrigins("http://localhost:4200", "https://thughari.github.io")
            .withSockJS();
}
```

Make sure to adjust the allowed origins for production environments.

## Running Locally

1. **Build the Project**
   First, clean and package the project:

   ```sh
   ./mvnw clean package
   ```

2. **Run the Application**
   To start the backend, run:

   ```sh
   java -jar target/chat-0.0.1-SNAPSHOT.jar
   ```

3. **Access the Chat**
   The backend (and bundled frontend) will be available at [http://localhost:8080](http://localhost:8080).

## Docker

You can also run this backend in a Docker container:

1. **Build the Docker Image**

   ```sh
   docker build -t chat-backend .
   ```

2. **Run the Docker Container**

   ```sh
   docker run -p 8080:8082 chat-backend
   ```

This will make the application available at `http://localhost:8080` on your local machine.

## License

This project is licensed under the MIT License.

---

## Notes

* **For Production**:
  Ensure that you update the allowed origins in `WebSocketConfig.java` for enhanced security, especially when using external frontends.

* **Error Handling & Logging**:
  The application is designed with basic error handling. You can configure custom logging in `application.properties` or `application.yml`.

* **Frontend Customization**:
  To customize the bundled frontend, modify the files in `src/main/resources/static` or replace them with your own frontend resources.
