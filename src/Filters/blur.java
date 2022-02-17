package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class blur implements PixelFilter {

    public DImage processImage(DImage img) {



        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        red = blurImage(red);
        green = blurImage(green);
        blue = blurImage(blue);

        img.setColorChannels(red, green, blue);
        return img;
    }

    private final double[][] blurKernel =
            {
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49},
                    {1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49, 1.0/49}

            };

    public short[][] blurImage(short[][] inputImg) {

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
