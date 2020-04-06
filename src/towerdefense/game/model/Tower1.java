package towerdefense.game.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Tower1 extends Tower{
    private Point2D.Double position;
    private int level;
    private int price;
    private double range;
    private int fireRate;
    private int damage;
    private ArrayList<Target> tagets;
    private int maxTargetNumber;
    //levels ?
    //private int health; (optionnel)

    public Tower1 (Point2D.Double position, int level, int price, double range, int fireRate, int damage){
        this.position = position;
        this.level = level;
        this.price = price;
        this.range = range;
        this.fireRate =fireRate;
        this.damage =damage;
    }
}
