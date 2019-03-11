package model;

/**
 * Model for Notification
 */
public class Notification {

    private long receivedTimestamp;
    private long sentTimestamp;
    private String event;

    public Notification(long receivedTimestamp, long sentTimestamp, String event) {
        this.receivedTimestamp = receivedTimestamp;
        this.sentTimestamp = sentTimestamp;
        this.event = event;
    }

    public long getReceivedTimestamp() {
        return receivedTimestamp;
    }

    public long getSentTimestamp() {
        return sentTimestamp;
    }

    public String getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "received=" + receivedTimestamp +
                ", sent=" + sentTimestamp +
                ", event='" + event + '\'' +
                '}';
    }
}
