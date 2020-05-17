package org.vadtel.gridsort;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<List<String>> dataList = readFile(args[0]);
        sort(dataList);
        writeFile(args[1], dataList);
    }

    private static List<List<String>> readFile(String path) throws IOException {
        List<List<String>> dataList = Files.lines(Paths.get(path))
                .map(s -> Arrays.asList(s.split("\t")))
                .collect(Collectors.toList());
        return dataList;
    }

    private static void writeFile(String path, List<List<String>> sortedData) throws IOException {
        FileWriter writer = new FileWriter(path);
        for (List<String> list : sortedData) {
            for (String str : list) {
                writer.write(str + "\t");
            }
            writer.write(System.lineSeparator());

        }
        writer.close();
    }

    private static void sort(List<List<String>> dataList) {
        Collections.sort(dataList, (o1, o2) -> {
            int i = 0;
            while (true) {
                String s1 = o1.get(i);
                String s2 = o2.get(i);
                if (s1.compareTo(s2) == 0) {
                    i++;
                    continue;
                }
                if (s1.isEmpty() || s1.equals(" ")) {
                    return 1;
                } else if (s2.isEmpty() || s2.equals(" ")) {
                    return -1;
                } else {
                    return s1.compareTo(s2);
                }
            }
        });
    }
}
