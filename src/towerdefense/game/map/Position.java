package towerdefense.game.map;

import towerdefense.game.model.Map;

public class Position{
    private double x;
    private double y;

    //***** Initialisation de la classe *****
    private static void initializePositionSystem(){

    }

    private static void changePositionSystem(){

    }

    //***** Construteurs *****

    public Position(){
        x = 0;
        y = 0;
    }

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    //***** Setters *******

    public void setPositionX(double x){this.x = x;}

    public void setPositionY(double y){this.y = y;}

    //***** Getters *******

    public double getPositionX(){return x;}

    public double getPositionY(){return y;}

    public int getPixelPositionX(){
        return (int) Math.round(x * Map.getPixelsPerMeter());
    }

    public int getPixelPositionY(){
        return (int) Math.round(y * Map.getPixelsPerMeter());
    }

    public double getNorm(){
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }

    public static double getNorm(double X, double Y){
        return Math.sqrt(Math.pow(X,2)+Math.pow(Y,2));
    }

    //public Pair getPosition(){
        //Pair<Double, Double> point = new Pair<Double, Double>(x,y);
        //return point;
    //}

    //***** Autres *****

    public double getDistance(Position p){
        double varX = x - p.getPositionX();
        double varY = y - p.getPositionY();
        return getNorm(varX, varY);
    }

    public void normalize(){
        double norm = this.getNorm();
        x /= norm;
        y /= norm;
    }
}
