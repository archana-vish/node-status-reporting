package service;

import exceptions.InvalidInputException;
import model.Node;
import model.NodeStatusReport;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class NodeExtractorTest {

    NodeExtractor nodeExtractor = new NodeExtractor();
    InputFileReader inputFileReader = new InputFileReader();
    List<NodeStatusReport> report;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void whenNull_exceptionThrow() throws InvalidInputException {
        exceptionRule.expect(InvalidInputException.class);
        exceptionRule.expectMessage("Please specify a valid input file.");
        this.inputFileReader.readFile(null);
    }

    @Test
    public void whenNoFilePassed_exceptionThrow() throws InvalidInputException {
        exceptionRule.expect(InvalidInputException.class);
        exceptionRule.expectMessage("Please specify a valid input file.");
        this.inputFileReader.readFile(new String[]{});
    }

    @Test
    public void whenEmptyFilePassed_exceptionThrow() throws InvalidInputException {
        exceptionRule.expect(InvalidInputException.class);
        exceptionRule.expectMessage("Error in reading input file. Please check input.");
        this.inputFileReader.readFile(new String[]{"empty.txt"});
    }


    @Test
    public void when_valid_lines_report_is_successful() throws InvalidInputException {
        List<String> lines = this.inputFileReader.readFile(new String[]{"src/test/resources/input.txt"});
        report = nodeExtractor.printFinalReport(lines);
        assertEquals(4, report.size());
     }

    @Test
    public void test_invalid_notification_lines() throws InvalidInputException {
        List<String> lines = this.inputFileReader.readFile(new String[]{"src/test/resources/invalidNotification.txt"});
        report = nodeExtractor.printFinalReport(lines);
        assertEquals(3, report.size());
    }


    @Test
    public void test_unknown_notification_status() throws InvalidInputException {
        List<String> lines = this.inputFileReader.readFile(new String[]{"src/test/resources/unknownstatus.txt"});
        report = nodeExtractor.printFinalReport(lines);
        assertEquals(report.get(1).getStatus(), Node.STATUS.UNKNOWN);
    }

    @Test
    public void test_unknown_notification_input() throws InvalidInputException {
        List<String> lines = this.inputFileReader.readFile(new String[]{"src/test/resources/unknownstatus.txt"});
        report = nodeExtractor.printFinalReport(lines);
        assertEquals(4, report.size());
    }

    @Test
    public void test_distinct_lines() throws InvalidInputException {
        List<String> lines = this.inputFileReader.readFile(new String[]{"src/test/resources/distinctnodes.txt"});
        report = nodeExtractor.printFinalReport(lines);
        assertEquals(4, report.size());
    }

    @Test
    public void test_same_node_lines() throws InvalidInputException {
        List<String> lines = this.inputFileReader.readFile(new String[]{"src/test/resources/samenode.txt"});
        report = nodeExtractor.printFinalReport(lines);
        assertEquals(1, report.size());

    }

}