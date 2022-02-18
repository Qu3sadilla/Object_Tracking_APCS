package Filters;

import Interfaces.Drawable;
import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

import java.nio.channels.Pipe;
import java.util.ArrayList;
//
public class Pipeline implements PixelFilter, Drawable {
    ArrayList<PixelFilter> filters = new ArrayList<>();

    public Pipeline() {
        PixelFilter outline = new blur();
        PixelFilter colorMask = new ColorMask();
        PixelFilter klustering = new klustering();

        filters.add(outline);
        filters.add(colorMask);
        filters.add(klustering);


    }

    @Override
    public DImage processImage(DImage img) {
        for(PixelFilter filter : filters) {
            img = filter.processImage(img);
        } return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
    }
}
