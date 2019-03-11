package model;

public class Node {

    static long counter = 1;

    public enum  STATUS {
        ALIVE,
        DEAD,
        UNKNOWN;
    };

    long id;
    String name;
    long emittedTime;
    STATUS status;
    long linkedNode;

    public String getName() {
        return name;
    }

    public long getEmittedTime() {
        return emittedTime;
    }

    public STATUS getStatus() {
        return status;
    }

    public long getLinkedNode() {
        return linkedNode;
    }

    public void setLinkedNode(long linkedNode) {
        this.linkedNode = linkedNode;
    }

    public long getId() {
        return id;
    }

    public Node(String name, long emittedTime, STATUS status) {
        this.id = counter++;
        this.name = name;
        this.emittedTime = emittedTime;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emittedTime=" + emittedTime +
                ", status=" + status +
                ", linkedNode=" + linkedNode +
                '}';
    }
}
