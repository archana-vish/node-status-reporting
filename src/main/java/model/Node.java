package model;

public class Node {

    public enum  STATUS {
        ALIVE,
        DEAD,
        UNKNOWN;
    };

    String name;
    long activeTime;
    STATUS status;

    public Node(String name, long activeTime, STATUS status) {
        this.name = name;
        this.activeTime = activeTime;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", activeTime=" + activeTime +
                ", status=" + status +
                '}';
    }
}
