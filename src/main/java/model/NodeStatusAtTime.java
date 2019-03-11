package model;

public class NodeStatusAtTime {

    private String name;
    private long time;
    private Node.STATUS status;

    public NodeStatusAtTime(String name, long time, Node.STATUS status) {
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public NodeStatusAtTime(String name, long time) {
        this.name = name;
        this.time = time;
       // this.status = status;
    }

    public long getTime() {
        return time;
    }

    public Node.STATUS getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NodeStatusAtTime{" +
                "name='" + name + '\'' +
                ", time=" + time
//                +
//                ", status=" + status
                +
                '}';
    }
}
