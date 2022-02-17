package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class blur2 implements PixelFilter {
    private double[][] embossKernel =
            {
                    {1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
                    {1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
                    {1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
                    {1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
                    {1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25},
            };

    public DImage processImage(DImage img) {

        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        for (int i = 1; i < red.length-24; i++) {
            for (int j = 1; j < red[0].length-24; j++) {
                red[i][j] = kernelExecute(img.getRedChannel(),embossKernel,i,j);
                blue[i][j] = kernelExecute(img.getBlueChannel(),embossKernel,i,j);
                green[i][j] = kernelExecute(img.getGreenChannel(),embossKernel,i,j);
            }
        }


        img.setColorChannels(red,green,blue);

        return img;
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
