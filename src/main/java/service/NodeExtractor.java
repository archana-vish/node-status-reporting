package service;

import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeStatus;
import model.Node;
import model.NodeStatusReport;
import model.Notification;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

public class NodeExtractor implements  NodeExtractorRules {

    private static Logger LOG = Logger.getLogger(NodeExtractor.class.getName());

    List<Notification> notificationList;
    List<Node> nodeList;
    List<NodeStatusReport> nodeStatusReportsList;

    public List<Notification> createNotification(List<String> lines) {
        ListIterator<String> itr = lines.listIterator();

        notificationList = new ArrayList<>();
        nodeList = new ArrayList<>();
        nodeStatusReportsList = new ArrayList<>();

        Notification notification;
        Node node;

        String line;

        try {

            while (itr.hasNext()) {
                line = itr.next();
                String[] elements = line.split("\\s");
                if (!validNotifications.contains(elements[NOTIFICATION_TYPE])) {
                    LOG.severe("Invalid notification status for line :: " + line);
                } else {
                    notification = createNotification(Long.parseLong(elements[RECEIVED_TIME]),
                            Long.parseLong(elements[SENT_FROM_NODE_TIME]),
                            String.join(" ", Arrays.asList(elements).subList(NodeExtractor.NODE_NAME, elements.length)));
                    notificationList.add(notification);

                    // Create nodes
                    node = createNode(elements[NODE_NAME], Long.parseLong(elements[SENT_FROM_NODE_TIME]),Node.STATUS.ALIVE);


                    if (!elements[NOTIFICATION_TYPE].equals("HELLO")) {

                        Node prevNode = node;

                        node = createNode(elements[OBSERVED_NODE],
                                Long.parseLong(elements[SENT_FROM_NODE_TIME]),
                                elements[NodeExtractor.NOTIFICATION_TYPE].equals("LOST") ? Node.STATUS.DEAD : Node.STATUS.ALIVE);

                        prevNode.setLinkedNode(node.getId());
                        node.setLinkedNode(prevNode.getId());

                        nodeStatusReportsList.add(createNodeStatusReport(prevNode.getName(), prevNode.getStatus(),
                                notification.getReceivedTimestamp(), prevNode.getActiveTime(), prevNode.getLinkedNode(), notification.getEvent()));
                        nodeStatusReportsList.add(createNodeStatusReport(node.getName(), node.getStatus(),
                                notification.getReceivedTimestamp(), node.getActiveTime(), node.getLinkedNode(), notification.getEvent()));


                        nodeList.add(prevNode);
                        nodeList.add(node);
                    } else {
                        nodeStatusReportsList.add(createNodeStatusReport(node.getName(), node.getStatus(),
                                notification.getReceivedTimestamp(), node.getActiveTime(), node.getLinkedNode(), notification.getEvent()));
                        nodeList.add(node);
                    }
                }
            }


            // Create outputFile
            LOG.info("Printing combined elements" + nodeStatusReportsList);

        } catch(Exception exception) {
            LOG.severe("Invalid notification status for line");
        }
//        LOG.info("Notification list::"+ notificationList);
//        LOG.info("Node list :: " + nodeList);
        return notificationList;
    }

    public Notification createNotification(long receivedTime, long sentTime, String message) {
        return new Notification(receivedTime, sentTime, message);
    }

    public Node createNode(String nodeName, long emittedTime, Node.STATUS status) {
        return new Node(nodeName, emittedTime, status);
    }

    public NodeStatusReport createNodeStatusReport(String nodeName, Node.STATUS status, long receivedTime, long emittedTime, long linkedNode, String message) {
        return new NodeStatusReport(nodeName, status, receivedTime, emittedTime, linkedNode, message);
    }

    public void messWithNodes() {


        Map<String, Optional<NodeStatusReport>> finalReport = nodeStatusReportsList.stream().collect(
                groupingBy(NodeStatusReport::getName,
                        maxBy((Comparator.comparingLong(NodeStatusReport::getEmittedTime)))));

        List<NodeStatusReport> finalNodes = finalReport.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        finalNodes.forEach(System.out::println);


    }
}
