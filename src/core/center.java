package core;

import java.util.ArrayList;

public class center {

    int x;
    int y;
    ArrayList<point> points = new ArrayList<point>();

    public center(int xlocation, int ylocation){
        x = xlocation;
        y = ylocation;
    }

    public void setPointsList(point[] list){
        for (int i = 0; i < list.length; i++) {
            points.add(list[i]);
        }
    }

    public void addPoint(point p){
        points.add(p);
    }

    public ArrayList<point> getClusterPoints(){
        if(points != null){
            return points;
        }else{
            return null;
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
