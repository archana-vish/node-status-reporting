package service;

import exceptions.InvalidInputException;
import exceptions.InvalidNotificationStatusException;
import model.Notification;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class NodeExtractorTest {

    NodeExtractor nodeExtractor = new NodeExtractor();
    InputFileReader inputFileReader = new InputFileReader();

    @Test
    public void test_valid_lines() throws InvalidInputException, IOException {
        List<String> lines = this.inputFileReader.readFile(new String[]{"input.txt"});
        List<Notification> notificationList = nodeExtractor.createNotification(lines);
        assertEquals(lines.size(), notificationList.size());
    }

    @Test
    public void test_invalid_notification_lines() throws InvalidInputException, IOException {
        List<String> lines = this.inputFileReader.readFile(new String[]{"invalidNotification.txt"});
        List<Notification> notificationList = nodeExtractor.createNotification(lines);
        assertEquals(lines.size()-2, notificationList.size());
    }

}