package towerdefense.game.towers;

import towerdefense.game.map.Position;
import towerdefense.game.interfaces.*;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;


public abstract class Tower implements Buyable, Upgradable, Placeable, Drawable, ProducesGold {
    protected Position position;
    protected int level;
    static  int maxLevel;
    protected int price;
    protected double range;
    protected int fireRate;
    protected int damageDeal;
    protected ArrayList<NPC> targets;
    protected int maxTargetNumber;
    //private int health; (optionnel)

    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

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

    public boolean canBeLeveledUp(){
        if (level < maxLevel) {
            return true;
        } else {
            return false;
        }
    }

    public int levelUp(Tower tower){
        if (tower.canBeLeveledUp()) {
            level++;
        }
    }

    //******Traitement des cibles*******

    public void hit(){
        String res;
        for (NPC target : targets){
            res = target.decreaseHealth(damageDeal);
            if (res == "is dead"){
                targets.remove(target);
            }
        }
    }

    //ne fonctionne pas : si deux targets sont mortes, seule la première sera retirée de la liste /!\ voir diagramme de séquence pour une autre méthode.

    //*******Production d'or*******

    public int retrievesGold(){
        int res = (int) Math.floor(0.5*price);
        return res;
    }

    public int producesGold(){} //récupére le goldLoot de chaque ennemi tué, comment connait-elle cette information ?

    //*******Autres*******

    public void run(){}

    public void updateDrawing(){}

    public String toString(){
        return "Tour :\n - position: " + position + "\n" +
                "- level: " + level + "\n"+
                "- maxLevel: " + maxLevel + "\n"+
                "- range: " + range + "\n"+
                "- fireRate: " + fireRate + "\n"+
                "- damageDeal: " + damageDeal + "\n"+
                "- maxTargetNumber: " + maxTargetNumber + "\n"+
                "- Nombre de cibles attaquées: " + targets.size() + "\n"+
                "- price: " + price + ".";
    }

}
