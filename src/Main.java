import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {

        // Create a new HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Create a new handler for incoming HTTP requests
        HttpHandler handler = new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {

                // Set the content type
                exchange.getResponseHeaders().add("Content-Type", "text/html");

                // Read the index.html file
                String html = new String(Files.readAllBytes(Paths.get("www/index.html")), StandardCharsets.UTF_8);

                // Send the response
                exchange.sendResponseHeaders(200, html.getBytes().length);
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(html.getBytes());
                responseBody.close();
            }
        };

        // Map the handler to the root path of the server
        server.createContext("/", handler);

        // Start the server
        server.start();

        System.out.println("Server started on port 8080");
    }
}
