package towerdefense.game.towers;

import towerdefense.game.interfaces.*;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;


public abstract class Tower implements Buyable, Upgradable, Placeable, Drawable, ProducesGold {
    protected Position position;
    protected int level;
    static int maxLevel;
    protected int price;
    protected int priceIncrement;
    protected double range;
    protected int fireRate;
    protected int damageDeal;
    protected ArrayList<NPC> targets;
    protected ArrayList<NPC> KIATargets;
    protected int maxTargetNumber;
    protected int totalGoldLoot;
    //private int health; (optionnel)


    public Tower(){ //TODO: lancer les threads
        maxLevel = 3;
    }

    //******Getteurs******

    public int getLevel(){return level;};

    public int getCost(){return price;}

    public double getRange(){return range;}

    public int getDamageDeal(){return damageDeal;}

    public Position getPos(){return position;}


    //*******Passage de niveau*******

    public boolean canBeLeveledUp(){
        boolean res = false;
        if (level< maxLevel){
            res = true;
        }
        return res;
    }

    public abstract void levelUp();

    //******Traitement des cibles*******

    public void targetAcquisition(ArrayList<NPC> npcs) {
        if (targets.size() < maxTargetNumber) {
            for (NPC npc : npcs) {
                if ((npc.getPos()).getDistance(position) <= range)
                    targets.add(npc);
            }
        }
    }

    public abstract ArrayList<NPC> hit(ArrayList<NPC> npcs);

    //*******Production d'or******* // TODO:

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
