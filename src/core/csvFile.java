package core;

import Filters.FindCenter;
import Interfaces.PixelFilter;
import core.DImage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class csvFile implements PixelFilter {
    public static void main(String[] args) throws IOException {
        File centers = new File("centers");
        writeDataToFile("centers", FindCenter.data);
        System.out.println(readFile("centers"));

    }

    public static void writeDataToFile(String filePath, String data) {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {

            writer.println(data);

        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }

    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    @Override
    public DImage processImage(DImage img) {
        return null;
    }
}