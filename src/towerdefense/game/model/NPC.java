package towerdefense.game.model;

import java.awt.geom.Point2D;

public abstract class NPC implements Hittable{
    protected int health;
    private int loot;
    //private ArrayList<Weapon> weapons; (optionnel)

    public NPC (){
        health =30;
        loot = 1;
    }

    //******Getteurs******

    public int getHealth() {return health;}

    public int getLoot(){return loot;}

    //******Autres******

    public void hit(int damage){
        if (health > damage){
            health -= damage;
        }else{health = 0;
        }
    }

    public void kill(NPC npc) {npc = null;}


}
