package game.model;

import java.awt.geom.Point2D;

public class Enemy1 extends NPC implements Attacks{
    private int health;
    private float speed;

    public Enemy1 (Point2D.Double position, Path path, int health, float speed){ //loot ?
        super(position,path);
        this.health = health;
        this.speed = speed;
    }
    //Contrôle héritage :
    public void getEnemyAttributs() {
        System.out.println("Les attributs de la classe Enemy1 sont :");
        System.out.println("position : "+super.position+", " +"path : "+ super.path+", "+"speed : "+speed+", " +"health : "+ health+".");
    }
}
