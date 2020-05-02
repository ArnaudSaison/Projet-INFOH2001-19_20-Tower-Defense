package towerdefense.game.towers;

import towerdefense.game.Buyable;
import towerdefense.game.Drawable;
import towerdefense.game.Placeable;
import towerdefense.game.Upgradable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;

public abstract class Tower implements Buyable, Upgradable, Placeable, Drawable, Runnable {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Attributs relatifs au passage de niveau:
    protected int level;
    static int maxLevel;
    protected int price;
    protected int priceIncrement;

    //Attributs de specification:
    protected double range;
    protected int fireRate;
    protected int damageDeal;
    protected ArrayList<NPC> targets;
    protected int maxTargetNumber;

    //Autres
    protected Position position;
    protected GameModel gameModel;
    protected Thread tTower;

    //Optionel:
    //private int health; (optionnel)

    //TODO: gestion du prix via un fichier properties ?
    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public Tower(Map map, GameModel gameModel, int range, int fireRate, int damageDeal, int maxTargetNumber){
        level = 1;
        maxLevel = 3;

        position = new Position(map);
        targets = new ArrayList<>();
        tTower = new Thread();

        this.gameModel = gameModel;
        this.range = range;
        this.fireRate = fireRate;
        this.damageDeal = damageDeal;
        this.maxTargetNumber = maxTargetNumber;
    }

    /*==================================================================================================================
                                                TRAITEMENT DES CIBLES
    ==================================================================================================================*/
    /**Test si les ennemis sur la carte sont à portée de tir.*/
    public void targetAcquisition() {
        ArrayList<NPC> npcsOnMap = gameModel.getNPCsOnMap();
        if (targets.size() < maxTargetNumber) {
            for (NPC npc: npcsOnMap) {
                if ((npc.getPos()).getDistance(position) <= range)
                    targets.add(npc);
            }
        }
    }

    public void hit(){
        targetAcquisition();
    }

    /*==================================================================================================================
                                                    PASSAGE DE NIVEAU
    ==================================================================================================================*/
    @Override
    public boolean canBeLeveledUp(){
        return level<maxLevel;
    }

    @Override
    public void levelUp(){
        if(canBeLeveledUp()){
            price = +priceIncrement;
        }
    } //TODO: à redéfinir dans les sous-classes.

    /*==================================================================================================================
                                                     GESTION DU THREAD
    ==================================================================================================================*/
    public void initialize(){
        tTower.start();
    }

    @Override
    public void run(){}

    @Override
    public void updateDrawing(){}

    /*==================================================================================================================
                                                    AUTRES
    ==================================================================================================================*/
    @Override
    public String toString(){
        return "Tour :\n - position: " + position + "\n" +
                "- level: " + level + "\n"+
                "- maxLevel: " + maxLevel + "\n"+
                "- range: " + range + "\n"+
                "- fireRate: " + fireRate + "\n"+
                "- damageDeal: " + damageDeal + "\n"+
                "- maxTargetNumber: " + maxTargetNumber + "\n"+
                "- Nombre de cibles attaquées: " + targets.size() + "\n"+
                "- price: " + price;
    }

    /*==================================================================================================================
                                                    GETTEURS
    ==================================================================================================================*/
    @Override
    public int getCost(){return price;}

    @Override
    public Position getPos(){return position;}
}
