package towerdefense.game.model;

import java.awt.geom.Point2D;

public class MainModelTester {
    public static void main(String args[]) {
        Point2D.Double p = new Point2D.Double();
        p.setLocation(1,1);
        Point2D.Double p1 = new Point2D.Double();
        p1.setLocation(2.15,5.27);



        Path pa = new Path(p1);
        Enemy1 e = new Enemy1(p,pa,10,5);
        e.getEnemyAttributs();
    }
}
