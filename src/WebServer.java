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



public class    WebServer {
    private ServerSocket serverSocket;
    private ExecutorService service;

    public static void main(String[] args) throws IOException {
        int port = 8081;
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
        service = Executors.newCachedThreadPool();
        server.setExecutor(service);
        server.createContext("/www", new RequestHandler());
        server.start();


        System.out.println("HELLO");

        //Socket clientSocket = serverSocket.accept();

       // BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    }

    private void serverRequests(ServerSocket serverSocket, ExecutorService service) {
    }

    private static class RequestHandler implements HttpHandler{

        HttpExchange exchange;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String request = null;
            if ("GET".equals(exchange.getRequestMethod())) {
                request = handleGetRequest(exchange);
                System.out.println(request);
            }
            System.out.println("TESTE do handle");
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
            //Path htmlResponsePath = Paths.get("www/index.html");
            /*StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(htmlResponsePath.toFile()));
            String line = br.readLine();

            while (line != null){
                stringBuilder.append(line);
                br.readLine();
            }

             */
            String html = new String(Files.readAllBytes(Paths.get("www/index.html")), StandardCharsets.UTF_8);

            httpExchange.sendResponseHeaders(200, html.getBytes().length);

            outputStream.write(html.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}