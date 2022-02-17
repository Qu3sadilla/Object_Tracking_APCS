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

        red = blur(red);
        green = blur(green);
        blue = blur(blue);

        trackRed(red, green, blue);
        trackBlue(red, green, blue);
        trackGreen(red, green, blue);

        img.setColorChannels(red, green, blue);
        return img;
    }

    //
    private void trackRed(short[][] red, short[][] green, short[][] blue) {
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (computeColorDistance(168, 44, 42, red[r][c], blue[r][c], green[r][c]) < 50) {
                    red[r][c] = WHITE;
                    green[r][c] = WHITE;
                    blue[r][c] = WHITE;
                } else {
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
                if (computeColorDistance(33, 34, 98, red[r][c], blue[r][c], green[r][c]) < 50) {
                    red[r][c] = WHITE;
                    green[r][c] = WHITE;
                    blue[r][c] = WHITE;
                } else {
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
                if (computeColorDistance(52, 98, 59, red[r][c], blue[r][c], green[r][c]) < 50) {
                    red[r][c] = WHITE;
                    green[r][c] = WHITE;
                    blue[r][c] = WHITE;
                } else {
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
        return Math.sqrt(differenceRed * differenceRed + differenceGreen * differenceGreen + differenceBlue * differenceBlue);
    }

    private double colorDistance(int targetRed, short r) {
        int differenceRed = targetRed - r;

        return Math.sqrt(differenceRed * differenceRed);
    }

    private double[][] blurKernel =
            {
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49}

            };

    public short[][] blur(short[][] inputImg) {

        short[][] pixels = inputImg;
        short[][] outputPixels = new short[inputImg.length][inputImg[0].length];

        for (int i = 0; i < inputImg.length; i++) {
            for (int j = 0; j < inputImg[0].length; j++) {
                outputPixels[i][j] = kernelExecute(inputImg, blurKernel, i, j);
            }
        }

        return outputPixels;

    }

    public short kernelExecute(short[][] inputImg, double[][] kernel, int y, int x) {
        double kernelSum = getKernelSum(kernel);
        double out = 0;
        double n = kernel.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int xloc, yloc;
                double kernelVal = kernel[i][j];

                if(y+i >= inputImg.length){
                    yloc = (y+i) - inputImg.length;
                }else{
                    yloc = y+i;
                }

                if(x+j >= inputImg[0].length){
                    xloc = (x+j) - inputImg[0].length;
                }else{
                    xloc = x+j;
                }

                double pixelVal = inputImg[yloc][xloc];
                out += pixelVal*kernelVal;
            }
        }

        if (out < 0){
            out = 0;
        }
        if(out > 255){
            out = 255;
        }

        if(kernelSum != 0)out = out/kernelSum;
        return (short) out;
    }

    private double getKernelSum(double[][] kernel) {
        double sum = 0;
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                sum += kernel[i][j];
            }
        }
        return sum;
    }
}
