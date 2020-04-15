package towerdefense.game.map;

public class Position{
    // x et y sont en coordonnées métriques, cela permet de changer l'échelle à volonté par la suite
    private double x;
    private double y;

    // Cette classe indique la postion sur la map :
    private Map map;

    private double tileMetricWidth;

    //***** Initialisation de la classe *****
    public void attachMap(Map map){
        this.map = map;
        this.tileMetricWidth = map.getTileMetricWidth();
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

    // Créer une position à partir de coordonnées de cases
    public Position(int x, int y, double tileMetricWidth){
        this.tileMetricWidth = tileMetricWidth;
        this.x = x * tileMetricWidth;
        this.y = y * tileMetricWidth;
    }

    //***** Setters *******
    public void setPositionX(double x){
        this.x = x;
    }

    public void setPositionY(double y){
        this.y = y;
    }

    //***** Getters *******
    public double getMetricPositionX(){
        return x;
    }

    public double getMetricPositionY(){
        return y;
    }

    public int getPixelPositionX(){
        return (int) Math.round(x * map.getPixelsPerMeter());
    }

    public int getPixelPositionY(){
        return (int) Math.round(y * map.getPixelsPerMeter());
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
        double varX = x - p.getMetricPositionX();
        double varY = y - p.getMetricPositionY();
        return getNorm(varX, varY);
    }

    public void normalize(){
        double norm = this.getNorm();
        x /= norm;
        y /= norm;
    }
}
