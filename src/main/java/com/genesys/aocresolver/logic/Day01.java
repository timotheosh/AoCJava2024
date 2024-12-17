package com.genesys.aocresolver.logic;

import io.vavr.collection.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day01 {
    public static Optional<String> loadResource(String fileName) {
        // Load file from resources directory
        ClassLoader classLoader = Day01.class.getClassLoader();
        Optional<String> content = Optional.empty();
        try (InputStream inputStream = classLoader.getResourceAsStream(Paths.get("input", fileName).toString())) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found! Check the path.");
            }
            // Convert input stream to a string
            content = Optional.ofNullable(new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static List<List<Integer>> processStringToLists(String fileData) {
        List<Integer> listOne = List.ofAll(fileData.lines())
                .map(line -> line.split("\\s+")[0])
                .map(Integer::parseInt)
                .sorted();
        List<Integer> listTwo = List.ofAll(fileData.lines())
                .map(line -> line.split("\\s+")[1])
                .map(Integer::parseInt)
                .sorted();
        return List.of(listOne, listTwo);
    }

    public static Number difference(List<List<Integer>> listData) {
        Number difference = listData.get(0)
                .zip(listData.get(1))
                .map(pair -> Math.abs(pair._1 - pair._2))
                .sum();
        return difference;
    }
}
