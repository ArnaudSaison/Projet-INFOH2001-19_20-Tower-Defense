package towerdefense.game.model;

import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Point2D;

public class Position{

    private double x;
    private double y;

    //*********Construteurs*********

    public Position(){
        x=0;
        y=0;
    }

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    //*********Setteurs***********

    public void setPositionX(double x){this.x = x;}

    public void setPositionY(double y){this.y = y;}

    //*********Getteurs***********

    public double getPositionX(){return x;}

    public double getPositionY(){return y;}

    //public Pair getPosition(){
        //Pair<Double, Double> point = new Pair<Double, Double>(x,y);
        //return point;
    //}

    //******Autres******

    public double getDistance(Position p){
        return Math.sqrt(Math.pow(x-p.getPositionX(),2)+Math.pow(y-p.getPositionY(),2));
    }
}
