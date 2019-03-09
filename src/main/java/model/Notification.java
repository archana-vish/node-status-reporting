package model;

public class Notification {

    private long received;
    private String event;

    public Notification(long received, String event) {
        this.received = received;
        this.event = event;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "received=" + received +
                ", event='" + event + '\'' +
                '}';
    }
}
