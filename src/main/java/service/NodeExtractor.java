package service;

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

                        nodeStatusReportsList.add(createNodeStatusReport(prevNode, notification));
                        nodeStatusReportsList.add(createNodeStatusReport(node, notification));

                        nodeList.add(prevNode);
                        nodeList.add(node);
                    } else {
                        nodeStatusReportsList.add(createNodeStatusReport(node, notification));
                        nodeList.add(node);
                    }
                }
            }

            //TODO remove this comment
            // Create outputFile
//            Comparator<NodeStatusReport> compareByName = Comparator.comparing(NodeStatusReport::getName);
//            Comparator<NodeStatusReport> compareByReceivedTime = Comparator.comparing(NodeStatusReport::getReceivedTime);
//
//            nodeStatusReportsList.stream()
//                    .sorted(compareByName.thenComparing(compareByReceivedTime));
                   // .forEach(System.out::println);
        } catch(Exception exception) {
            LOG.severe("Invalid notification status for line");
        }
        return notificationList;
    }

    private Notification createNotification(long receivedTime, long sentTime, String message) {
        return new Notification(receivedTime, sentTime, message);
    }

    private Node createNode(String nodeName, long emittedTime, Node.STATUS status) {
        return new Node(nodeName, emittedTime, status);
    }

    private NodeStatusReport createNodeStatusReport(Node node, Notification notification) {
        return new NodeStatusReport(node.getId(), node.getName(), node.getStatus(), notification.getReceivedTimestamp(), node.getActiveTime(), node.getLinkedNode(), notification.getEvent());
    }

    public void findUnknownAndPrintFinalReport() {

        List<NodeStatusReport> nodeStatusReports = this.nodeStatusReportsList;
        Map<Long, List<NodeStatusReport>> groupedByTime = nodeStatusReports.stream().collect(
                groupingBy(NodeStatusReport::getEmittedTime)
        );

        List<List<NodeStatusReport>> incorrectOnes  = new ArrayList<>();
        Iterator<Long> itr = groupedByTime.keySet().iterator();
        while (itr.hasNext()) {
            long time = itr.next();
            if (groupedByTime.get(time).size() > 2) {
                incorrectOnes.add(groupedByTime.get(time));
            }
        }

        List<NodeStatusReport> allIncorrectList = incorrectOnes.stream().flatMap(List::stream).collect(Collectors.toList());

        ListIterator<NodeStatusReport> itr2 = nodeStatusReports.listIterator();
        while (itr2.hasNext()) {
            NodeStatusReport report = itr2.next();
            if (allIncorrectList.contains(report)) {
                report.setStatus(Node.STATUS.UNKNOWN);
                itr2.set(report);
            }
        }

        Map<String, Optional<NodeStatusReport>> finalReport = nodeStatusReports.stream().collect(
                groupingBy(NodeStatusReport::getName,
                        maxBy((Comparator.comparingLong(NodeStatusReport::getEmittedTime)))));


        List<NodeStatusReport> finalNodes = finalReport.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        finalNodes.forEach(System.out::println);

    }
}
