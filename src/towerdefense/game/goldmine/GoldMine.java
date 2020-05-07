package towerdefense.game.goldmine;

import towerdefense.game.*;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.view.Printable;

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
    private int price; //prix que le joueur doit payer pour acheter/améliorer la mine d'or.
    private int productionRate; //quantité d'or produit en une minute.
    private int goldStorage; //quantité d'or stockée par la mine d'or, nécessite une action du joueur pour être récupérée.
    private int maxGoldStorage; //quantité d'or maximale que la mine d'or peut stocker.

    //Autres:
    private Position position;
    private GameModel gameModel;
    private Thread tGoldMine;
    private Boolean running; //Permet de vérifier que le thread de la mine d'or est activé.

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    /**
     * Constructeur.
     * @param goldMineSpe = [[level1Spec], ..., [levelNSpec]]
     */
    public GoldMine(Map map, GameModel gameModel, ArrayList<ArrayList<Integer>> goldMineSpe) {
        this.gameModel = gameModel;

        //Initialisation du niveau:
        this.goldMineSpe = goldMineSpe;
        level = 1;
        maxLevel = 3;

        //Initialisation des attributs:
        ArrayList<Integer> level1Spe = goldMineSpe.get(0);
        setAttributes(level1Spe);

        //Initialisation de la position et du thread:
        position = new Position(map);
        tGoldMine = new Thread(this);
        running = false;
    }

    /*==================================================================================================================
                                                   PRODUCTION DE L'OR
    ==================================================================================================================*/
    /**Permet de produire de l'or qui sera ensuite stocké*/
    public void produceGold(){
        if(goldStorage < maxGoldStorage){
            goldStorage++;
        }
    }

    /**Permet de récupérer l'or stocké*/
    public int retrieveGold(){                     //TODO: récuperer l'action du joueur.
        int res = goldStorage;
        goldStorage = 0;
        return res;
    }

    /*==================================================================================================================
                                                   PASSAGE DE NIVEAU
    ==================================================================================================================*/
    public boolean canBeLeveledUp() {
        return level < maxLevel;
    }

    /**Si le niveau maximal n'est pas atteint, passe les attributs à leur valeur spécifiée par la liste levelSpe (cf: setAttributes()) */
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            ArrayList<Integer> levelSpe = goldMineSpe.get(level-1);
            setAttributes(levelSpe);
        }
    }

    /**Passe tous les attributs à leur valeur spécifiée par la liste prise en argument.
     * @param levelSpe = [int price, int productionRate, int maxGoldStorage].
     */
    private void setAttributes(ArrayList<Integer> levelSpe){
        price = levelSpe.get(0);
        productionRate = levelSpe.get(1);
        maxGoldStorage = levelSpe.get(2);
    }

    /*==================================================================================================================
                                                   GESTION DU THREAD
    ==================================================================================================================*/
    /**Démarre le thread de la mine d'or*/
    public void initialize(){
        running = true;
        tGoldMine.start();
    }

    /**Définition du thread de la mine d'or: si le jeu n'est pas en pause alors produit de l'or à la cadence imposée par
     * l'attribut productionRate, sinon place le thread en pause*/
    public void run(){
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

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/
    public void updateDrawing(){}

    public Printable getDrawing() {
        return null;
    }

    public void removeDrawing() {}

    /*==================================================================================================================
                                                   GETTEURS/SETTEURS
    ==================================================================================================================*/
    public int getCost(){
        return price;
    }

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