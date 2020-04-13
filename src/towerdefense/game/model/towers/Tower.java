package towerdefense.game.model.towers;

import towerdefense.game.map.Position;
import towerdefense.game.model.interfaces.*;
import towerdefense.game.model.npcs.NPC;

import java.util.ArrayList;


public abstract class Tower implements Buyable, Upgradable, Placeable, Drawable, ProducesGold {
    protected Position position;
    static int level;
    protected  int maxLevel;
    protected int price;
    protected double range;
    protected int fireRate;
    protected int damageDeal;
    protected ArrayList<NPC> targets;
    protected int maxTargetNumber;
    //private int health; (optionnel)

    public Tower(){
        level = 1;
        price = 10;
        range = 3; //en mètre.
        fireRate = 3;// coups/seconde
        damageDeal = 1;// en point de vie
        targets = new ArrayList<NPC>();
        maxTargetNumber = 5;
    }

    //******Getteurs******

    public int getLevel(){return level;}

    public int getPrice(){return price;}

    public double getRange(){return range;}

    public int getDamageDeal(){return damageDeal;}

    public Position getPos(){return position;}


    //*******Passage de niveau*******

    public boolean canBeLeveledUp(int maxLevel){}

    public int levelUp(){}

    //******Traitement des cibles*******

    public void hit(int damageDeal){}

    //*******Production d'or*******

    public int retrievesGold(){}

    public int producesGold(){}
    //*******Autres*******

    public void run(){}

    public void updateDrawing(){}

    public String toString(){}

}
