package server.server;

import java.sql.SQLException;

import common.db.Database;
import common.packet.Message;

public class Server {

    private Database database;

    public Server() {
        try {
            this.database = new Database("server_db");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleMessage(Message message) {
        try {
            this.database.insertMessage(message);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
