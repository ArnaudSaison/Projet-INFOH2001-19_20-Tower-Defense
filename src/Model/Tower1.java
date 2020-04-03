package Model;

import java.awt.geom.Point2D;

public class Tower1 extends Tower{
    private int level;
    private int price;
    private double range;
    private int fireRate;
    private int damage;
    //private int health; (optionnel)

    public Tower1 (Point2D.Double position, int level, int price, double range, int fireRate, int damage){
        super(position);
        this.level = level;
        this.price = price;
        this.range = range;
        this.fireRate =fireRate;
        this.damage =damage;
    }
}
