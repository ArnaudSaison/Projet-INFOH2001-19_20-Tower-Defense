package towerdefense.game.map;

public class Position{
    // x et y sont en coordonnées métriques, cela permet de changer l'échelle à volonté par la suite
    private double x;
    private double y;

    // Cette classe indique la postion sur la map :
    private Map map;

    private double tileMetricWidth;

    //***** Initialisation de la classe *****
    /** Méthode réservée à l'appel par MapFactory
     * */
    public void attachMap(Map map){
        this.map = map;
        this.tileMetricWidth = map.getTileMetricWidth();
    }

    //***** Construteurs *****
    public Position(Map map){
        x = 0;
        y = 0;
    }

    public Position(double x, double y, Map map){
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y, Map map, boolean convertToMeters){
        this.map = map;
        if (convertToMeters){
            this.x = x / map.getPixelsPerMeter();
            this.y = y / map.getPixelsPerMeter();
        } else {
            this.x = x;
            this.y = y;
        }
    }

    // Créer une position à partir de coordonnées de cases
    /** Méthode est réservée à l'appel par MapFactory
     * */
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
    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getMetricX(){
        return x;
    }

    public double getMetricY(){
        return y;
    }

    public double getPixelX(){
        return Math.round(x * map.getPixelsPerMeter());
    }

    public double getPixelY(){
        return Math.round(y * map.getPixelsPerMeter());
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
        double varX = x - p.getX();
        double varY = y - p.getY();
        return getNorm(varX, varY);
    }

    public void normalize(){
        double norm = this.getNorm();
        x /= norm;
        y /= norm;
    }

    // Opérateurs
    public void multiply(double fact){
        this.x *= fact;
        this.y *= fact;
    }

    public Position getMultiplied(double fact){
        return new Position(this.getX() * fact, this.getY() * fact, map);
    }

    public void add(Position pos2){
        this.x += pos2.getX();
        this.y += pos2.getY();
    }

    public Position getAdded(Position pos2){
        return new Position(this.x + pos2.getX(), this.y + pos2.getY(), map);
    }

    public void substract(Position pos2){
        this.x -= pos2.getX();
        this.y -= pos2.getY();
    }

    public Position getSubstracted(Position pos2){
        return new Position(this.x - pos2.getX(), this.y - pos2.getY(), map);
    }

    // Représentation dans la console
    @Override
    public String toString() {
        return "(" + x + " ; " + y + ")";
    }

    public String toString(String txt) {
        return txt + " (" + x + " ; " + y + ")";
    }
}
