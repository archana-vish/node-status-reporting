import exceptions.InvalidInputException;
import exceptions.InvalidNotificationStatusException;
import model.Notification;
import service.InputFileReader;
import service.NodeExtractor;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Main main = new Main();
            InputFileReader inputFileReader = new InputFileReader();
            NodeExtractor nodeExtractor = new NodeExtractor();

            List<String> lines = inputFileReader.readFile(args);
            List<Notification> notifications = nodeExtractor.createNotification(lines);

        } catch(InvalidInputException exception) {
            exception.printStackTrace();
        }
    }
}
