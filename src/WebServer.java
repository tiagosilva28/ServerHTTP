import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import util.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class WebServer {
    private ServerSocket serverSocket;
    private ExecutorService service;

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (System.getenv("PORT") != null) {
            port = Integer.parseInt(System.getenv("PORT"));
        }
        try {
            new WebServer().start(port);
        } catch (IOException e) {
            System.err.println(Messages.NO_SUCH_COMMAND);
        }
    }

    private void start(int port) throws IOException {

        // Initialize server socket and thread pool
        // serverSocket = new ServerSocket(port);
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/www", new RequestHandler());
        service = Executors.newCachedThreadPool();
        server.setExecutor(service);
        server.start();

        //Socket clientSocket = serverSocket.accept();

       // BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    }

    private void serverRequests(ServerSocket serverSocket, ExecutorService service) {
    }

    private static class RequestHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String request = null;
            if ("GET".equals(exchange.getRequestMethod())) {
                request = handleGetRequest(exchange);
                System.out.println(request);
            }
            handleResponse(exchange);
        }

        private String handleGetRequest(HttpExchange httpExchange) {
            return httpExchange.
                    getRequestURI()
                    .toString()
                    .split("\\?")[1]
                    .split("=")[1];
        }

        private void handleResponse(HttpExchange httpExchange) throws IOException {

            OutputStream outputStream = httpExchange.getResponseBody();
            String htmlResponse = String.valueOf(Paths.get("www/index.html"));

            httpExchange.sendResponseHeaders(200, htmlResponse.length());

            outputStream.write(htmlResponse.getBytes());
            outputStream.flush();
            outputStream.close();

        }
    }
}