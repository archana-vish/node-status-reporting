package service;

import exceptions.InvalidInputException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class InputFileReaderTest {

    InputFileReader inputFileReader;

    @Test(expected = InvalidInputException.class)
    public void test_for_empty_file_name() throws InvalidInputException, IOException {
        String[] args = new String[0];
        this.inputFileReader = new InputFileReader();
        List<String> lines = this.inputFileReader.readFile(args);
        assertEquals(0, lines.size());
    }

    @Test
    public void test_for_some_invalid_lines_in_file() throws InvalidInputException, IOException {
        this.inputFileReader = new InputFileReader();
        List<String> lines = this.inputFileReader.readFile(new String[]{"someinvalidLines.txt"});
        assertEquals(4, lines.size());
    }

    @Test
    public void test_for_all_invalidlines_in_file() throws InvalidInputException, IOException {
        this.inputFileReader = new InputFileReader();
        List<String> lines = this.inputFileReader.readFile(new String[]{"invalidlines.txt"});
        System.out.println(lines.size());
        assertEquals(0, lines.size());
    }

    @Test
    public void test_for_all_validlines_in_file() throws InvalidInputException, IOException {
        this.inputFileReader = new InputFileReader();
        List<String> lines = this.inputFileReader.readFile(new String[]{"input.txt"});
        System.out.println(lines.size());
        assertEquals(7, lines.size());
    }
}