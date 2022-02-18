package core;

public class center {

    int x;
    int y;
    point[] points;

    public center(int xlocation, int ylocation){
        x = xlocation;
        y = ylocation;
    }

    public void setPointsList(point[] list){
        for (int i = 0; i < list.length; i++) {
            points = list;
        }
    }

    public point[] getClusterPoints(){
        if(points != null){
            return points;
        }else{
            return null;
        }
    }

    public int getCenterX(){
        return x;
    }

    public int getCenterY(){
        return y;
    }
}
