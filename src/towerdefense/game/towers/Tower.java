package towerdefense.game.towers;

import towerdefense.game.interfaces.*;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;


public class Tower implements Buyable, Upgradable, Placeable, Drawable, ProducesGold {
    private Position position;
    private int level;
    static  int maxLevel = 3;
    private int price;
    private int priceIncrement;
    private double range;
    private int fireRate;
    private int damageDeal;
    private ArrayList<NPC> targets;
    private ArrayList<NPC> KIATargets;
    private int maxTargetNumber;
    private int totalGoldLoot;
    //private int health; (optionnel)

    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

    public Tower(Map map){
        Position position = new Position(map);
        level = 1;
        price = 10;
        priceIncrement = 3*level;
        range = 3; //en mètre.
        fireRate = 3;// coups/seconde
        damageDeal = 1;// en point de vie
        targets = new ArrayList<NPC>();
        KIATargets = new ArrayList<NPC>();
        maxTargetNumber = 5;
    }

    //******Getteurs******

    public int getLevel(){return level;}

    public int getCost(){return price;}

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

    public void levelUp(){
        if (this.canBeLeveledUp()) {
            level++;
            price += priceIncrement;
        }
    }

    //******Traitement des cibles*******

    public void targetAcquisition(ArrayList<NPC> npcs){
        if (targets.size() < maxTargetNumber){
            for (NPC npc : npcs){
                if ((npc.getPos()).getDistance(position) <= range )
                targets.add(npc);
            }
        }
    }

    /** A chacune des cibles applique un dommage, retire la cible de la liste des cibles dont la tour s'occupe si la cible meurt ou est hors de portée.
     * Permet de savoir quelles cibles ont été détruites.**/
    public ArrayList<NPC> hit(){
        String res;
        ArrayList<NPC> toRemove = new ArrayList<NPC>();
        for (NPC target : targets){
            res = target.decreaseHealth(damageDeal);
            if (res == "is dead"){
                toRemove.add(target);
                KIATargets.add(target);
            } else if ((target.getPos()).getDistance(position) >= range) {
                toRemove.add(target);
            }
        }
        targets.remove(toRemove);
        return KIATargets;
    }

    //*******Production d'or*******

    public int retrieveGold(){
        return totalGoldLoot;
    }

    public void produceGold(){
        totalGoldLoot = 0;
        for (NPC fallenSoldier : KIATargets){
            totalGoldLoot = fallenSoldier.getGoldLoot();
        }
    }

    //*******Autres*******
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
