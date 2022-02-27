package server.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.packet.Message;

import server.server.Server;

public class Client extends Thread {
    private boolean running;

    private Socket sock;
    private Server server;

    private ObjectInputStream objInStream;
    private ObjectOutputStream objOutStream;

    public Client(Server server, Socket sock) throws IOException{
        super(
            "Client@" +
            sock.getInetAddress().getHostAddress() + ":" +
            String.valueOf(sock.getPort())
        );

        this.sock = sock;
        this.server = server;
        this.objInStream = new ObjectInputStream(sock.getInputStream());
        this.objOutStream = new ObjectOutputStream(sock.getOutputStream());
    }

    public void run() {
        this.running = true;
        while (this.running) {
            try {
                Object object = this.objInStream.readObject();
                this.handlePacketObject(object);
            } catch (SocketException | EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            this.sock.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendObject(Object object) throws IOException {
        this.objOutStream.writeObject(object);
    }

    public void handlePacketObject(Object object) {
        if (object instanceof Message) {
            server.handleMessage((Message)object);
        }
    }
}
