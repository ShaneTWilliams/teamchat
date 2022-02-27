package client.main;

import java.io.*;
import java.net.*;
import java.time.*;

import common.constants.Constants;
import common.packet.Message;

public class Main {

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket(Constants.SERVER_LOCAL_IP, Constants.SERVER_PORT);
        Message msg = new Message("shane_williams", 1, "Message contents here", OffsetDateTime.now());

        ObjectOutputStream obj_stream = new ObjectOutputStream(sock.getOutputStream());
        obj_stream.writeObject(msg);
        obj_stream.flush();
        obj_stream.close();
        sock.close();
    }
}
