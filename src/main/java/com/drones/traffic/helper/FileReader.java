package com.drones.traffic.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
/**
 * Created by jcarretero on 10/05/2018.
 */
public class FileReader {

    private static final Logger log = LoggerFactory.getLogger(FileReader.class);

    public static void readFile(String filename, Consumer<String> actionPerLine) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
            try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                br.lines().forEach(actionPerLine);
            }
        } catch (Exception ex) {
            log.error("Error FileReader", ex);
        }
    }
}
