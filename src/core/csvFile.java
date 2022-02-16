package core;

import Filters.FindCenter;
import Interfaces.PixelFilter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class csvFile implements PixelFilter {

    private static double[] fake_data = {42.3,56.1,784.0};

    public static void main(String[] args) throws IOException {
        writeDataToFile("centers", FindCenter.data);
        System.out.println(readFile("centers.txt"));

    }
    public static void writeDataToFile(String filePath, String data) {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b))  {

            writer.println(fake_data);


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
