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

//        for (int i = 0; i < centers.length; i++) {
//            System.out.println(centers[i].getX() + ", " + centers[i].getY());
//        }

        points = findHighlighted(pixels);

        centers = makePointLists(points, centers);

        placeCenters(centers);

        if(num == 1) {
            for (int i = 0; i < centers.length; i++) {
                System.out.println(centers[i].getX() + ", " + centers[i].getY());
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
        for (int i = 0; i < centers.length; i++) {
            double sumX = 0;
            double sumY = 0;
            ArrayList<point> pointList = centers[i].getPointsList();

            double size = pointList.size();

            for (int j = 0; j < pointList.size(); j++) {
                sumX += pointList.get(j).getX();
                sumY += pointList.get(j).getY();
            }

            int Xloc = (int) (sumX/ size);
            int Yloc = (int) (sumY/ size);

            centers[i].setX(Xloc);
            centers[i].setY(Yloc);
        }
    }

    private ArrayList<point> findHighlighted(short[][] input){
        ArrayList<point> points = new ArrayList<point>();
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                if (input[row][col] == 255){
                    point p = new point(row,col);
                    points.add(p);
                }
            }
        }
        return points;
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
