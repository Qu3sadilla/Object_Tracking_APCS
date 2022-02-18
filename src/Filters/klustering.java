package Filters;

import Interfaces.PixelFilter;
import core.DImage;
import core.center;

public class klustering implements PixelFilter {

    public DImage processImage(DImage img) {

        // Written by Jacob Nixon 02/2022

        short[][] pixels = img.getBWPixelGrid();

        placeCenters(pixels);

        return img;
    }

    public center[] placeCenters(short[][] img){
        center[] centers = new center[3];
        return centers;
    }

}
