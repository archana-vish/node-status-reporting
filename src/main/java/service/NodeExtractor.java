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

    private List<Notification> createNotification(List<String> lines) {
        ListIterator<String> itr = lines.listIterator();

        notificationList = new ArrayList<>();
        nodeList = new ArrayList<>();
        nodeStatusReportsList = new ArrayList<>();

        Notification notification;
        Node node;
        Node prevNode;
        String line;

        try {
            while (itr.hasNext()) {
                line = itr.next();
                String[] data = line.split("\\s");
                if (!validNotifications.contains(data[NOTIFICATION_TYPE])) {
                    LOG.severe("Invalid notification status for line :: " + line);
                } else {
                    notification = createNotification(data);
                    notificationList.add(notification);

                    // Create nodes
                    node = createNode(data, false);

                    if (!data[NOTIFICATION_TYPE].equals("HELLO")) {

                        prevNode = node;

                        node = createNode(data, true);

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
        } catch(Exception exception) {
            LOG.severe("Invalid notification status for line");
        }
        return notificationList;
    }

    private Notification createNotification(String[] line) {
       return new Notification (
                Long.parseLong(line[RECEIVED_TIME]),
                Long.parseLong(line[SENT_FROM_NODE_TIME]),
                String.join(" ", Arrays.asList(line).subList(NodeExtractor.NODE_NAME, line.length)));
    }

    private Node createNode(String[] data, boolean observedNode) {
        return new Node(
                observedNode? data[OBSERVED_NODE] :  data[NODE_NAME] ,
                Long.parseLong(data[SENT_FROM_NODE_TIME]),
                observedNode
                        ? data[NodeExtractor.NOTIFICATION_TYPE].equals("LOST") ? Node.STATUS.DEAD : Node.STATUS.ALIVE
                        :  Node.STATUS.ALIVE
        );
    }

    private NodeStatusReport createNodeStatusReport(Node node, Notification notification) {
        return new NodeStatusReport(node.getId(), node.getName(), node.getStatus(), notification.getReceivedTimestamp(), node.getEmittedTime(), node.getLinkedNode(), notification.getEvent());
    }

    public List<NodeStatusReport>  printFinalReport(List<String> lines) {
        createNotification(lines);
        return printFinalReport();
    }

    private List<NodeStatusReport> printFinalReport() {

        List<NodeStatusReport> nodeStatusReports = this.nodeStatusReportsList;
        Map<Long, List<NodeStatusReport>> groupedByTime = nodeStatusReports.stream().collect(
                groupingBy(NodeStatusReport::getEmittedTime)
        );

        List<List<NodeStatusReport>> incorrectOnes  = new ArrayList<>();
        for (Long time : groupedByTime.keySet()) {
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

        return finalNodes;

    }
}
