package towerdefense.game.goldmine;

import towerdefense.game.*;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.view.Printable;
import towerdefense.view.npc.NPCView;

public class GoldMine implements ProducesGold, Buyable, Upgradable, Placeable, Drawable, Runnable {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Attributs relatifs au passage de niveau:
    private int level;
    private int maxLevel;

    //Attributs de specification:
    private int price;
    private int priceIncrement;
    private int productionRate; //temps de pause du thread (en milliseconde).
    private int productionRateIncrement;
    private int goldStorage;
    private int maxGoldStorage;
    private int maxGoldStorageIncrement;

    //Autres:
    private Position position;
    private GameModel gameModel;
    private Thread tGoldMine;
    private Boolean runing;

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public GoldMine(Map map, int maxLevel, int price, int priceIncrement, int productionRate,int productionRateIncrement, int maxGoldStorage, int maxGoldStorageIncrement ) {
        level = 1;
        this.maxLevel = maxLevel;

        this.price = price;
        this.priceIncrement = priceIncrement;
        this.productionRate = productionRate;
        this.productionRateIncrement = productionRateIncrement;
        this.maxGoldStorage = maxGoldStorage;
        this.maxGoldStorageIncrement = maxGoldStorageIncrement;

        position = new Position(map);
        tGoldMine = new Thread(this);
        runing = false;
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
            price += priceIncrement;
            productionRate += productionRateIncrement;
            maxGoldStorage += maxGoldStorageIncrement;
        }
    }

    /*==================================================================================================================
                                                   GESTION DU THREAD
    ==================================================================================================================*/
    public void initialize(){
        runing = true;
        tGoldMine.start();
    }

    public void run(){

        while (runing) {
            while (!gameModel.getPaused()) {
                try {
                    produceGold();
                    Thread.sleep(productionRate);
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
                                                    JavaFX
    ==================================================================================================================*/
    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    public void initDrawing() {
//        npcView = new NPCView(this, map, "");
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    public void updateDrawing() {
//        npcView.update();
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    public Printable getDrawing() {
//        return npcView;
        return null;
    }


    /*==================================================================================================================
                                                   GETTEURS
    ==================================================================================================================*/
    @Override
    public int getCost(){
        return price;
    } //static ?

    @Override
    public Position getPos(){
        return position;
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