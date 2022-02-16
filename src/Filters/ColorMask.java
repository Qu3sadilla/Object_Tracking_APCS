package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class ColorMask implements PixelFilter {
    private static final short WHITE = 255;
    private static final short BLACK = 0;

    @Override
    public DImage processImage(DImage img) {
        int targetRed = Integer.parseInt(JOptionPane.showInputDialog("Enter red target color (0-255) "));
        int targetGreen = Integer.parseInt(JOptionPane.showInputDialog("Enter red target color (0-255) "));
        int targetBlue = Integer.parseInt(JOptionPane.showInputDialog("Enter red target color (0-255) "));
        double threshold = Double.parseDouble(JOptionPane.showInputDialog("Enter threshold from 0 to 442"));

        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();

        int height = blue.length;
        int width = blue[0].length;
        double colorDist;

        short[][] newImage = new short[height][width];

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                colorDist = computerColorDistance(targetRed, targetBlue, targetGreen, red[r][c], green[r][c], blue[r][c]);
                if (colorDist < threshold) {
                    newImage[r][c] = WHITE;
                } else {
                    newImage[r][c] = BLACK;
                }
            }
        }
        img.setColorChannels(red, green, blue);
        return img;    }

    private double computerColorDistance(int targetRed, int targetBlue, int targetGreen, short r, short g, short b) {
        int differenceRed = targetRed - r;
        int differenceGreen = targetGreen - g;
        int differenceBlue = targetBlue - b;
        return Math.sqrt(differenceRed*differenceRed+differenceGreen*differenceGreen+differenceBlue*differenceBlue);
    }
}
