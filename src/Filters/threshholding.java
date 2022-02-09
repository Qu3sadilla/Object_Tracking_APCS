package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class threshholding implements PixelFilter {

    public DImage processImage(DImage img) {

        short[][] pixels = img.getBWPixelGrid();
        short[][] outputPixels;

        outputPixels = threshhold(pixels);

        img.setPixels(outputPixels);

        return img;
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

}
