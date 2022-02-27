package common.packet;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class Message implements Serializable {
    public final String sender;
    public final int chatID;
    public final String content;
    public final OffsetDateTime dateTime;

    public Message(String sender, int chatID, String content, OffsetDateTime dateTime) {
        this.sender = sender;
        this.chatID = chatID;
        this.content = content;
        this.dateTime = dateTime;
    }
}
