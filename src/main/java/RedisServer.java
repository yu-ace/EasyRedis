import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {
    private final RedisService redisService;

    public RedisServer() {
        redisService = new RedisService();
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Redis server is running on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String input;
            while ((input = reader.readLine()) != null) {
                String[] parts = input.split("\\s+");
                String response;

                if (parts.length < 1) {
                    response = "Error: Invalid command";
                } else {
                    switch (parts[0].toUpperCase()) {
                        case "GET":
                            if (parts.length != 2) {
                                response = "Error: GET command requires one key";
                            } else {
                                response = redisService.get(parts[1]);
                            }
                            break;
                        case "SET":
                            if (parts.length != 3) {
                                response = "Error: SET command requires key and value";
                            } else {
                                response = redisService.set(parts[1], parts[2]);
                            }
                            break;
                        default:
                            response = "Error: Unsupported command";
                    }
                }
                writer.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RedisServer server = new RedisServer();
        server.start(6379);
    }
}