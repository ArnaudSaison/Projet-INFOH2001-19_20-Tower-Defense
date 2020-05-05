package towerdefense.game.goldmine;

import towerdefense.game.*;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

import java.util.ArrayList;

public class GoldMine implements ProducesGold, Buyable, Upgradable, Placeable, Drawable, Runnable {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Attributs relatifs au passage de niveau:
    private int level;
    static int maxLevel;

    //Attributs de specification:
    private ArrayList<ArrayList<Integer>> goldMineSpe;
    private int price;
    private int productionRate; //quantité d'or produit en une minute.
    private int goldStorage;
    private int maxGoldStorage;

    //Autres:
    private Position position;
    private GameModel gameModel;
    private Thread tGoldMine;
    private Boolean running;

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public GoldMine(Map map, GameModel gameModel, ArrayList<ArrayList<Integer>> goldMineSpe) {
        this.gameModel = gameModel;

        //Initialisation du niveau:
        this.goldMineSpe = goldMineSpe;
        level = 1;
        maxLevel = 3;

        //Initialisation des attributs:
        ArrayList<Integer> level1Spe = goldMineSpe.get(1);
        setAttributes(level1Spe);

        //Initialisation de la position et du thread:
        position = new Position(map);
        tGoldMine = new Thread(this);
        running = false;
    }

    /*==================================================================================================================
                                                   PRODUCTION DE L'OR
    ==================================================================================================================*/
    public void produceGold(){
        if(goldStorage < maxGoldStorage){
            goldStorage++;
        }
    }

    public int retrieveGold(){
        int res = goldStorage;
        goldStorage = 0;
        return res;
    }

    /*==================================================================================================================
                                                   PASSAGE DE NIVEAU
    ==================================================================================================================*/
    @Override
    public boolean canBeLeveledUp() {
        return level < maxLevel;
    }

    @Override
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            ArrayList<Integer> levelSpe = goldMineSpe.get(level-1);
            setAttributes(levelSpe);
        }
    }

    private void setAttributes(ArrayList<Integer> levelSpe){
        price = levelSpe.get(0);
        productionRate = levelSpe.get(1);
        maxGoldStorage = levelSpe.get(2);
    }

    /*==================================================================================================================
                                                   GESTION DU THREAD
    ==================================================================================================================*/
    public void initialize(){
        running = true;
        tGoldMine.start();
    }

    public void run(){
//todo: production rate
        while (running) {
            while (!gameModel.getPaused()) {
                try {
                    produceGold();
                    Thread.sleep(60000/productionRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        while (gameModel.getPaused()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateDrawing(){}

    /*==================================================================================================================
                                                   GETTEURS/SETTEURS
    ==================================================================================================================*/
    @Override
    public int getCost(){
        return price;
    }

    @Override
    public Position getPos(){
        return position;
    }

    @Override
    public void setPosition(Position position){
        this.position = position;
    }

    /*==================================================================================================================
                                                   AUTRES
    ==================================================================================================================*/
    @Override
    public String toString(){
        return "Mine d'or :\n - position: " + position + "\n" +
                "- level: " + level + "\n"+
                "- maxLevel: " + maxLevel + "\n"+
                "- maxGoldStorage: " + maxGoldStorage + "\n"+
                "- goldStorage: " + goldStorage + "\n"+
                "- productionRate: " + productionRate + "\n"+
                "- price: " + price + ".";
    }
}