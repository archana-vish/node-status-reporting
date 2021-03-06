package service;

import exceptions.InvalidInputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InputFileReader {

    private static Logger LOG = Logger.getLogger(InputFileReader.class.getName());

    /**
     * Method to read an input file and get the list of lines in the file
     * @param args the input file name
     * @return List<String> of lines
     * @throws InvalidInputException
     */
    public List<String> readFile(String[] args) throws InvalidInputException {

        String filename;

        if (args == null || args.length == 0) {
            LOG.severe("Please specify a valid input file.");
            throw new InvalidInputException("Please specify a valid input file.");
        } else {
            filename = args[0];
        }

        Path path = Paths.get(filename);
        List<String> lines;

        try {
           lines = Files.readAllLines(path);
           lines = lines.stream()
                   .filter(InputFileReader::validFormat)
                   .distinct()
                   .collect(Collectors.toList());

        } catch (IOException e) {
            LOG.severe("Error in reading input file. Please check input.");
            throw new InvalidInputException("Error in reading input file. Please check input.");
        }

        return lines;
    }

    /**
     * Check valid input format
     * @param line the input line
     * @return
     */
    private static boolean validFormat(String line) {
        return (line.matches("([0-9]+)\\s([0-9]+)\\s([a-z0-9]+)\\s([A-Z]+)(\\s)?([A-Z]*)\\s([a-z0-9]*)") ||
        line.matches("([0-9]+)\\s([0-9]+)\\s([a-z0-9]+)\\s([A-Z]+)"));
    }
}
