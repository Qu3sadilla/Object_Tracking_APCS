package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class colorThreshholding implements PixelFilter {
    public DImage processImage(DImage img) {

        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        short[][] outputPixels;

        outputPixels = threshhold(red,green,blue);

        img.setPixels(outputPixels);

        return img;
    }

    public short[][] threshhold(short[][] red, short[][] green, short[][] blue){
        short[][] output = new short[red.length][red[0].length];

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (runRatio(red[r][c],green[r][c],blue[r][c])) {
                    output[r][c] = 255;
                }else{
                    output[r][c] = 0;
                }
            }
        }
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (runRatio(green[r][c],red[r][c],blue[r][c])) {
                    output[r][c] = 255;
                }else{
                    output[r][c] = 0;
                }
            }
        }
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (runRatio(blue[r][c],green[r][c],red[r][c]) || runRatio(green[r][c],red[r][c],blue[r][c]) || runRatio(red[r][c],green[r][c],blue[r][c])) {
                    output[r][c] = 255;
                }else{
                    output[r][c] = 0;
                }
            }
        }

        return output;
    }

    public Boolean runRatio(short mainColor, short subcolor1, short subcolor2){

        if(mainColor/((subcolor1+subcolor2)+1) > 1){
            return true;
        }

        return false;
    }
}
