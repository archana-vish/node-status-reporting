package model;

public class NodeStatusReport {

    long id;
    String name;
    Node.STATUS status;
    long receivedTime;
    long emittedTime;
    long linkedNode;
    String message;

    public String getName() {
        return name;
    }

    public Node.STATUS getStatus() {
        return status;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public long getEmittedTime() {
        return emittedTime;
    }

    public long getLinkedNode() {
        return linkedNode;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }

    public void setStatus(Node.STATUS status) {
        this.status = status;
    }

    public NodeStatusReport(long id, String name, Node.STATUS status, long receivedTime, long emittedTime, long linkedNode, String message) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.receivedTime = receivedTime;
        this.emittedTime = emittedTime;
        this.linkedNode = linkedNode;
        this.message = message;
    }

    @Override
    public String toString() {
        return  name + " " + status + " " + receivedTime + " " + emittedTime + " " + message;
    }
}
