package service;

import exceptions.InvalidNotificationStatusException;
import model.Node;
import model.Notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

public class NodeExtractor {

    private static Logger LOG = Logger.getLogger(NodeExtractor.class.getName());

    List<Notification> notificationList;
    private static final List<String> validNotifications = Arrays.asList("HELLO", "LOST", "FOUND");
    private static final List<String> validNodeStatus = Arrays.asList("ALIVE", "DEAD", "UNKNOWN");

    public List<Notification> createNotification(List<String> lines) {
        ListIterator<String> itr = lines.listIterator();

        List<Notification> notificationList = new ArrayList<>();
        List<Node> nodeList = new ArrayList<>();

        String line;

        try {

            while (itr.hasNext()) {
                line = itr.next();
                String[] elements = line.split("\\s");
                if (!validNotifications.contains(elements[3])) {
//                    throw new InvalidNotificationStatusException("Invalid notification status for line " + line);
                    LOG.severe("Invalid notification status for line :: " + line);
                } else {
                    notificationList.add(
                            new Notification(Long.parseLong(elements[0]),
                                    String.join(" ", Arrays.asList(elements).subList(2, elements.length)))
                    );

                    // Create nodes

                    if (elements[3].equals("HELLO")) {
                        nodeList.add(
                                new Node(elements[2],
                                        Long.parseLong(elements[1]),
                                        Node.STATUS.ALIVE)
                        );
                    } else {
                        nodeList.add(
                                new Node(elements[4],
                                        Long.parseLong(elements[1]),
                                        Node.STATUS.ALIVE)
                        );
                    }
                }
            }
        } catch(Exception exception) {
            LOG.severe("Invalid notification status for line");
        }
        System.out.println(notificationList);
        System.out.println(nodeList);
        return notificationList;
    }

//    public void createNodes(List<String> lines) {
//        ListIterator<String> iterator = lines.listIterator();
//        String line;
//        while (iterator.hasNext()) {
//            line = iterator.next();
//            String[]
//            if (line.split(" ").length == 4) {
//                new Node(Long.parseLong())
//            }
//        }
//    }
}
