package service;

import java.util.Arrays;
import java.util.List;

public interface NodeExtractorRules {
    List<String> validNotifications = Arrays.asList("HELLO", "LOST", "FOUND");

    // SAMPLE LINES -->
    // 1508405807242 1508405807141 vader HELLO
    // 1508405807467 1508405807479 vader LOST r2d2

    int RECEIVED_TIME        = 0;
    int SENT_FROM_NODE_TIME  = 1;
    int NODE_NAME            = 2;
    int NOTIFICATION_TYPE    = 3;
    int OBSERVED_NODE        = 4;
}
