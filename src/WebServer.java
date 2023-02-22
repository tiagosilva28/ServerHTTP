import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import util.Messages;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class WebServer implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("TESTE do handle");
        //String response = "Hello, world!";
        String response = new String(Files.readAllBytes(Paths.get("www/index.html")), StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    /*private static class RequestHandler{


        private String handleGetRequest(HttpExchange httpExchange) {
            return httpExchange.
                    getRequestURI()
                    .toString()
                    .split("\\?")[1]
                    .split("=")[1];
        }

        private void handleResponse(HttpExchange httpExchange) throws IOException {
            String html = new String(Files.readAllBytes(Paths.get("www/index.html")), StandardCharsets.UTF_8);

            httpExchange.sendResponseHeaders(200, html.getBytes().length);

            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(html.getBytes());
            //outputStream.flush();
            outputStream.close();
        }
    }*/
    public static void main(String[] args) throws IOException {
        int port = 8081;
        if (System.getenv("PORT") != null) {
            port = Integer.parseInt(System.getenv("PORT"));
        }
        try {
            new WebServer().start();
        } catch (IOException e) {
            System.err.println(Messages.NO_SUCH_COMMAND);
        }

        /*HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new WebServer());
        server.start();
        System.out.println("Server started on port 8000");*/

    }

    private void start() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new WebServer());
        server.start();

        System.out.println("Server started");

    }
}