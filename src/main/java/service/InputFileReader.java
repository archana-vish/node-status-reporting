package service;

import exceptions.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InputFileReader {

    private static Logger LOG = Logger.getLogger(InputFileReader.class.getName());

    public List<String> readFile(String[] args) throws InvalidInputException {

        String filename = "";

        if (args.length == 0) {
            throw new InvalidInputException("Empty input file. Please check");
        } else {
            filename = args[0];
        }

        List<String> lines = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                this.getClass().getResourceAsStream("/" + filename)
        ))) {
           lines= in.lines()
                    .filter(InputFileReader::validFormat)
                    .distinct()
                    .collect(Collectors.toList());

        } catch(IOException exception) {
            LOG.severe("Error in reading input file. Please check input.");
        }

        return lines;
    }

    //TODO improve regex
    private static boolean validFormat(String line) {
        return (line.matches("([0-9]+)\\s([0-9]+)\\s([a-z0-9]+)\\s([A-Z]+)(\\s)?([A-Z]*)\\s([a-z0-9]*)") ||
        line.matches("([0-9]+)\\s([0-9]+)\\s([a-z0-9]+)\\s([A-Z]+)"));
    }
}
