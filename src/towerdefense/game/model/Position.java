package towerdefense.game.model;

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

    //******Autres******

    public double getDistance(Position p1, Position p2){
        return Math.sqrt(Math.pow(p1.getPositionX()-p2.getPositionX(),2)+Math.pow(p1.getPositionY()-p2.getPositionY(),2));
    }
}
