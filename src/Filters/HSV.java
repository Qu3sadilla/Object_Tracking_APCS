package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class HSV implements PixelFilter {
    static short[] rgb_to_hsv(double r, double g, double b) {

        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        r = r / 255.0;
        g = g / 255.0;
        b = b / 255.0;

        // h, s, v = hue, saturation, value
        double cmax = Math.max(r, Math.max(g, b)); // maximum of r, g, b
        double cmin = Math.min(r, Math.min(g, b)); // minimum of r, g, b
        double diff = cmax - cmin; // diff of cmax and cmin.
        double h = -1, s = -1;

        // if cmax and cmax are equal then h = 0
        if (cmax == cmin)
            h = 0;

            // if cmax equal r then compute h
        else if (cmax == r)
            h = (60 * ((g - b) / diff) + 360) % 360;

            // if cmax equal g then compute h
        else if (cmax == g)
            h = (60 * ((b - r) / diff) + 120) % 360;

            // if cmax equal b then compute h
        else if (cmax == b)
            h = (60 * ((r - g) / diff) + 240) % 360;

        // if cmax equal zero
        if (cmax == 0)
            s = 0;
        else
            s = (diff / cmax) * 100;

        // compute v
        double v = cmax * 100;
        //System.out.println("(" + h + " " + s + " " + v + ")");

        short[] hsv = {(short) h,(short) s,(short) v};
        return hsv;

    }

    @Override
    public DImage processImage(DImage img) {

        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        short h, s, v;

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                short [] hsv = rgb_to_hsv(red[r][c],green[r][c],blue[r][c]);
                h = (short) (hsv[0]/2.0);
                s = hsv[1];
                v = hsv[2];

                System.out.println(h + "," + s + "," + v);
                if((h < 10 || h > 170) && s > 50){
                    red[r][c] = 255;
                    blue[r][c] = 0;
                    green[r][c] = 0;
                }else if (h < 60 && h > 40 && s > 50){
                    System.out.println("gren");
                    red[r][c] = 0;
                    blue[r][c] = 0;
                }else if (h > 110 && h < 130 && s > 50){
                    System.out.println("blu");
                    red[r][c] = 0;
                    green[r][c] = 0;
                    blue[r][c] = 255;
                }else{
                    red[r][c] = 0;
                    green[r][c] = 0;
                    blue[r][c] = 0;
                }

//                short[] rgb = hsv_to_rgb(outputh[r][c],outputs[r][c],outputv[r][c]);
            }
        }

        img.setColorChannels(red,green,blue);

        return img;
    }

}
