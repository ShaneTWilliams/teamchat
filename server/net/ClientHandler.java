package server.net;

import java.io.InterruptedIOException;
import java.net.*;

import common.constants.Constants;
import server.net.Client;
import server.server.Server;

public class ClientHandler {

    public static void run() throws Exception {
        ServerSocket server_sock = new ServerSocket(Constants.SERVER_PORT);
        Server server = new Server();

        while (true) {
            Socket sock = server_sock.accept();
            Client client = new Client(server, sock);
            client.start();
        }
    }
}
