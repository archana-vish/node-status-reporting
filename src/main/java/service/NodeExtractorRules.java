package service;

import java.util.Arrays;
import java.util.List;

public interface NodeExtractorRules {
    List<String> validNotifications = Arrays.asList("HELLO", "LOST", "FOUND");
    List<String> validNodeStatus = Arrays.asList("ALIVE", "DEAD", "UNKNOWN");

    int RECEIVED_TIME        = 0;
    int SENT_FROM_NODE_TIME  = 1;
    int NODE_NAME            = 2;
    int NOTIFICATION_TYPE    = 3;
    int OBSERVED_NODE        = 4;
}
