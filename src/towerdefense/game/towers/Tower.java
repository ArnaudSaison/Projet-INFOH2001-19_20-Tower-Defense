package towerdefense.game.towers;

import towerdefense.game.map.Position;
import towerdefense.game.interfaces.*;
import towerdefense.game.model.GameModel;
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
    protected int enemyLoot; // à ajouter au diagramme.
    protected ArrayList<NPC> targetsKIA; // à ajouter au diagramme.
    //private int health; (optionnel)

    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

    public Tower(){
        level = 1;
        price = 10;
        range = 3; //en mètre.
        fireRate = 3;// coups/seconde
        damageDeal = 1;// en point de vie
        maxTargetNumber = 5;
        ArrayList<NPC> targets = new ArrayList<NPC>();
        ArrayList<NPC> targetsKIA = new ArrayList<NPC>();
    }

    //******Getteurs******

    public int getLevel(){return level;}

    public int getPrice(){return price;}

    public double getRange(){return range;}

    public int getDamageDeal(){return damageDeal;}

    public Position getPos(){return position;}

    public ArrayList<NPC> getTargetsKIA(){return targetsKIA;}// à ajouter au diagramme.


    //*******Passage de niveau*******

    public boolean canBeLeveledUp(){
        if (level < maxLevel) {
            return true;
        } else {
            return false;
        }
    }

    public void levelUp(Tower tower){
        if (tower.canBeLeveledUp()) {
            level++;
        }
    }

    //******Traitement des cibles*******

    public void setTargets(GameModel gameModel){
        ArrayList<NPC> NPCs = gameModel.getNPCs();
        for (NPC npc : NPCs){
            if ((position.getDistance(npc.getPos()) <= range) && (targets.size()< maxTargetNumber)){
                targets.add(npc);
            }
        }
    }// à ajouter au diagramme.

    public void hit(){
        String res;
        for (NPC target : targets){
            res = target.decreaseHealth(damageDeal);
            if (res == "is dead"){
                targetsKIA.add(target);
                enemyLoot += target.getGoldLoot();
            }
        }
        targets.remove(targetsKIA);
    }

    //*******Production d'or*******

    public int retrievesGold(){
        int res = (int) Math.floor(0.5*price);
        return res;
    }

    public int producesGold(){return enemyLoot;}

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
