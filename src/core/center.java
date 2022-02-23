package core;

import java.util.ArrayList;

public class center {

    int x;
    int y;
    ArrayList<point> points = new ArrayList<>();

    public center(int xlocation, int ylocation){
        x = xlocation;
        y = ylocation;
    }

    public ArrayList<point> getPointsList(){
        return points;
    }

    public void addPoint(point p){
        points.add(p);
    }


    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int loc){
        x = loc;
    }

    public void setY(int loc){
        y = loc;
    }
}
