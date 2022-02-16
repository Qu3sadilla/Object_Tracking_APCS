package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class ColorMask implements PixelFilter {
    private static final short WHITE = 255;
    private static final short BLACK = 0;

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        /*for (int r = 0; r < img.getHeight(); r++) {       //takes all red out
            for (int c = 0; c < img.getWidth(); c++) {
                if(computeColorDistance(255,0,0,));
            }
        }*/
        trackRed(red,green,blue);

        img.setColorChannels(red, green, blue);
        return img;
    }

    private void trackRed(short[][] red, short[][] green, short[][] blue) {
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if(computeColorDistance(168,44,42,red[r][c],blue[r][c],green[r][c])<50){
                    red[r][c] = WHITE;
                    green[r][c] = WHITE;
                    blue[r][c] = WHITE;
                } else{
                    red[r][c] = BLACK;
                    green[r][c] = BLACK;
                    blue[r][c] = BLACK;
                }
            }

        }
    }
    private void trackBlue(short[][] red, short[][] green, short[][] blue) {
        for (int r = 0; r < blue.length; r++) {
            for (int c = 0; c < blue[0].length; c++) {
                if(computeColorDistance(33,34,98,red[r][c],blue[r][c],green[r][c])<50){
                    red[r][c] = WHITE;
                    green[r][c] = WHITE;
                    blue[r][c] = WHITE;
                } else{
                    red[r][c] = BLACK;
                    green[r][c] = BLACK;
                    blue[r][c] = BLACK;
                }
            }

        }
    }
    private void trackGreen(short[][] red, short[][] green, short[][] blue) {
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if(computeColorDistance(52,98,59,red[r][c],blue[r][c],green[r][c])<50){
                    red[r][c] = WHITE;
                    green[r][c] = WHITE;
                    blue[r][c] = WHITE;
                } else{
                    red[r][c] = BLACK;
                    green[r][c] = BLACK;
                    blue[r][c] = BLACK;
                }
            }

        }
    }

    private double computeColorDistance(int targetRed, int targetBlue, int targetGreen, short r, short g, short b) {
        int differenceRed = targetRed - r;
        int differenceGreen = targetGreen - g;
        int differenceBlue = targetBlue - b;
        return Math.sqrt(differenceRed*differenceRed+differenceGreen*differenceGreen+differenceBlue*differenceBlue);
    }
    private double colorDistance(int targetRed,short r) {
        int differenceRed = targetRed - r;

        return Math.sqrt(differenceRed*differenceRed);
    }
}
