package Filters;

import Interfaces.PixelFilter;
import core.DImage;
import core.center;
import core.point;

import java.util.ArrayList;

public class klustering implements PixelFilter {

    private int num = 0;
    private center[] centers = new center[3];

    public DImage processImage(DImage img) {

        // Written by Jacob Nixon 02/2022

        short[][] pixels = img.getBWPixelGrid();
        ArrayList<point> points;

        //Initialize centers

        while(num == 0){
            for (int i = 0; i < centers.length; i++) {

                int random_x = (int) (Math.random()*pixels[0].length);
                int random_y = (int) (Math.random()*pixels.length);

                centers[i] = new center(random_x,random_y);
            }
            num++;
        }

        points = findPoints(pixels);

        centers = makePointLists(points, centers);

        placeCenters(centers);

        if(num == 1) {
            for (core.center center : centers) {
                System.out.println(center.getX() + ", " + center.getY());
            }
        }
        num++;
        if(num >= 1000){
            num = 1;
        }


        return img;
    }

    public center[] makePointLists(ArrayList<point> points, center[] centers){

        for (point p : points) {
            float[] distances = new float[centers.length];

            float minDistance = Integer.MAX_VALUE;
            int minDistanceIndex = 4;

            for (int i = 0; i < centers.length; i++) {
                int xc = centers[i].getX();
                int yc = centers[i].getY();

                int xp = p.getX();
                int yp = p.getY();

                float xd = xp-xc;
                float yd = yp-yc;

                distances[i] = (float) Math.sqrt((xd*xd)+(yd*yd));

            }

            for (int i = 0; i < distances.length; i++) {
                if(distances[i] <= minDistance){
                    minDistance = distances[i];
                    minDistanceIndex = i;
                }
            }

            centers[minDistanceIndex].addPoint(p);
        }

        return centers;
    }

    private void placeCenters(center[] centers){
        for (core.center center : centers) {
            double sumX = 0;
            double sumY = 0;
            ArrayList<point> pointList = center.getPointsList();

            double size = pointList.size();

            for (core.point point : pointList) {
                sumX += point.getX();
                sumY += point.getY();
            }

            int Xloc = (int) (sumX / size);
            int Yloc = (int) (sumY / size);

            center.setX(Xloc);
            center.setY(Yloc);
        }
    }

    private ArrayList<point> findPoints(short[][] input){
        ArrayList<point> points = new ArrayList<>();
        for (int row = 0; row < input.length - 2; row += 3) {
            for (int col = 0; col < input[0].length - 2; col += 3) {
                if (input[row][col] == 255){
                    if(isPoint(input,col,row)) {
                        point p = new point(row, col);
                        points.add(p);
                    }
                }
            }
        }
        return points;
    }

    private boolean isPoint(short[][] input, int x, int y){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (input[row+y][col+x] != 255){
                    return false;
                }
            }
        }
        return true;
    }



//    for (point p : points) {
//            float[] distances = new float[centers.length];
//
//            float minDistance = Integer.MAX_VALUE;
//            int minDistanceIndex = 4;
//
//            for (int i = 0; i < centers.length; i++) {
//                int xc = centers[i].getX();
//                int yc = centers[i].getY();
//
//                int xp = p.getX();
//                int yp = p.getY();
//
//                float xd = xp-xc;
//                float yd = yp-yc;
//
//                distances[i] = (float) Math.sqrt((xd*xd)+(yd*yd));
//
//            }
//
//            for (int i = 0; i < distances.length; i++) {
//                if(distances[i] <= minDistance){
//                    minDistance = distances[i];
//                    minDistanceIndex = i;
//                }
//            }
//
//            centers[minDistanceIndex].addPoint(p);
//        }

}
