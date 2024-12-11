package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RedisClient {
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public RedisClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String sendCommand(String command) throws IOException {
        writer.println(command);
        return reader.readLine();
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        RedisClient client = new RedisClient("localhost", 6379);
        try {
            client.connect();

            // 测试SET命令
            System.out.println("Testing SET command:");
            System.out.println(client.sendCommand("SET name redis"));
            System.out.println(client.sendCommand("SET age 12"));

            // 测试GET命令
            System.out.println("Testing GET command:");
            System.out.println(client.sendCommand("GET age"));
            System.out.println(client.sendCommand("GET name"));

            // 测试不存在的键
            System.out.println("Testing GET non-existent key:");
            System.out.println(client.sendCommand("GET time"));

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}