package org.vadtel.gridsort;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<List<String>> dataList = readFile(args[0]);
        sort(dataList);
        writeFile(args[1], dataList);
    }

    private static List<List<String>> readFile(String path) {
        List<List<String>> dataList = null;
        try {
            dataList = Files.lines(Paths.get(path))
                    .map(s -> Arrays.asList(s.split("\t")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла");
            System.exit(1);
        }
        //Check
        for (int i = 0; i < dataList.size() - 1; i++) {
            if (dataList.get(i) != dataList.get(i + 1)) {
                System.err.println("Данные в файле не вылидны.");
                System.exit(1);
            }
        }
        return dataList;
    }

    private static void writeFile(String path, List<List<String>> sortedData){

        try (FileWriter writer = new FileWriter(path)){
            for (List<String> list : sortedData) {
                for (String str : list) {
                    writer.write(str + "\t");
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e){
            System.err.println("Ошибка при записи файла");
            System.exit(1);
        }



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

                boolean isNumber1 = s1.matches("[-+]?\\d+");
                boolean isNumber2 = s2.matches("[-+]?\\d+");

                if (isNumber1 && isNumber2) {
                    Integer i1 = Integer.parseInt(s1);
                    Integer i2 = Integer.parseInt(s2);
                    return i1.compareTo(i2);
                }

                if (isNumber1) {
                    return -1;
                } else if (isNumber2) {
                    return 1;
                } else {
                    return s1.compareTo(s2);
                }
            }
        });
    }
}
