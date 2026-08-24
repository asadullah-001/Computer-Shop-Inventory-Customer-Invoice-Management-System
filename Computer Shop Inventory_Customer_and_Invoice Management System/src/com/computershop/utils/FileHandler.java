package com.computershop.utils;

import java.io.*;
import java.util.*;

public class FileHandler {
    @FunctionalInterface
    public interface StringToStringConverter<T> {
        String convert(T obj);
    }

    @FunctionalInterface
    public interface StringToObjectConverter<T> {
        T convert(String line);
    }

    public static <T> void writeToFile(String filename, List<T> list, StringToStringConverter<T> converter) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (T obj : list) {
                writer.write(converter.convert(obj) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static <T> List<T> readFromFile(String filename, StringToObjectConverter<T> converter) {
        List<T> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                T obj = converter.convert(scanner.nextLine());
                if (obj != null) list.add(obj);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, starting fresh.");
        }
        return list;
    }
}