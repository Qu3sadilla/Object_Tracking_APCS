package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class sobel implements PixelFilter {

    private double[][] embossKernel =
            {
                    {-2, -1, 0},
                    {-1, 1, 1},
                    {0, 1, 2}   };

    private double[][] sobelX =

            {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}   };

    private double[][] sobelY =

            {
                    {1, 2, 1},
                    {0, 0, 0},
                    {-1, -2, -1}   };


    public DImage processImage(DImage img) {

        short[][] pixels = img.getBWPixelGrid();
        short[][] outputPixels = img.getBWPixelGrid();

        for (int i = 1; i < pixels.length-2; i++) {
            for (int j = 1; j < pixels[0].length-2; j++) {
                outputPixels[i][j] = sobelLineDetection(pixels,i,j);
            }
        }

        outputPixels = threshhold(pixels);

        img.setPixels(outputPixels);

        return img;
    }


    public short sobelLineDetection(short[][] inputImg, int y, int x){
        short out;

        double xValue = kernelExecute(inputImg,sobelX,y,x);
        double yValue = kernelExecute(inputImg,sobelY,y,x);

        xValue = xValue*xValue;
        yValue = yValue*yValue;

        out = (short) Math.sqrt((xValue+yValue));

        return out;
    }

    public short[][] threshhold(short[][] inputImg){
        short[][] output = new short[inputImg.length][inputImg[0].length];

        for (int r = 0; r < inputImg.length; r++) {
            for (int c = 0; c < inputImg[0].length; c++) {
                if (inputImg[r][c] >= 50) {
                    output[r][c] = 0;
                }else{
                    output[r][c] = 255;
                }
            }
        }

        return output;
    }

    public short kernelExecute(short[][] inputImg, double[][] kernel, int y, int x) {
        double kernelSum = getKernelSum(kernel);
        double out = 0;
        double n = kernel.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double kernelVal = kernel[i][j];
                double pixelVal = inputImg[y+i][x+j];

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
