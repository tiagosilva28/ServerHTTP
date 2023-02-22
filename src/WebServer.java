import util.Messages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private ServerSocket serverSocket;
    private ExecutorService service;

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if(System.getenv("PORT") !=null){
            port = Integer.parseInt(System.getenv("PORT"));
        }
        try {
            new WebServer().start(port);
        } catch (IOException e){
            System.err.println(Messages.NO_SUCH_COMMAND);
        }
    }

    private void start(int port) throws IOException {

        // Initialize server socket and thread pool
        serverSocket = new ServerSocket(port);
        service = Executors.newCachedThreadPool();
        Socket clientSocket = serverSocket.accept();
    }

    private void serverRequests (ServerSocket serverSocket, ExecutorService service){

    }

    private static class RequestHandler implements Runnable{

        @Override
        public void run() {

        }
    }
}