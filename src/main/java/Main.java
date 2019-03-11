import exceptions.InvalidInputException;
import service.InputFileReader;
import service.NodeExtractor;

import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            InputFileReader inputFileReader = new InputFileReader();
            NodeExtractor nodeExtractor = new NodeExtractor();

            List<String> lines = inputFileReader.readFile(args);
            nodeExtractor.printFinalReport(lines);

        } catch(InvalidInputException exception) {
            log.severe(exception.getMessage());
        }
    }
}
