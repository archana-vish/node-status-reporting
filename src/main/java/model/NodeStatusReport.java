package model;

public class NodeStatusReport {

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

    public NodeStatusReport(String name, Node.STATUS status, long receivedTime, long emittedTime, long linkedNode, String message) {
        this.name = name;
        this.status = status;
        this.receivedTime = receivedTime;
        this.emittedTime = emittedTime;
        this.linkedNode = linkedNode;
        this.message = message;
    }

    @Override
    public String toString() {
        return name + " " + status + " " + receivedTime + " " + message;
    }
}
