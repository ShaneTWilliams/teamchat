package common.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

import common.constants.Constants;
import common.packet.Message;

public class Database {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL_PREFIX = "jdbc:h2:";

    private static final String USER = "sa";
    private static final String PASS = "";

    private Connection conn;

    public Database(String filename) throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        String currentPath = new File("").getAbsolutePath();
        String dbFilePath = DB_URL_PREFIX + currentPath + "/" + filename;
        this.conn = DriverManager.getConnection(dbFilePath, USER, PASS);

        Statement stmt = this.conn.createStatement();
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS messages (" +
                "msg_id INTEGER NOT NULL AUTO_INCREMENT," +
                "chat_id INTEGER NOT NULL," +
                "sender VARCHAR(" + Constants.USERNAME_MAX_LEN + ")," +
                "content VARCHAR(" + Constants.MESSAGE_MAX_LEN + ")," +
                "timestamp TIMESTAMP," +
                "PRIMARY KEY (msg_id)" +
            ");"
        );
        stmt.close();
    }

    public void insertMessage(Message message) throws SQLException {
        Timestamp timeStamp = new Timestamp(1000 * message.dateTime.toEpochSecond());
        PreparedStatement stmt = this.conn.prepareStatement(
            "INSERT INTO messages(chat_id, sender, content, timestamp) VALUES(?, ?, ?, ?)"
        );
        stmt.setInt(1, message.chatID);
        stmt.setString(2, message.sender);
        stmt.setString(3, message.content);
        stmt.setTimestamp(4, timeStamp);
        stmt.executeUpdate();
        stmt.close();
        System.out.println("Added message '" + message.content + "' to database.");
    }
}
