package towerdefense.game.goldmine;

import towerdefense.game.*;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

import java.lang.Runnable;

public class GoldMine implements ProducesGold, Buyable, Upgradable, Placeable, Drawable, Runnable {
    private Position position;
    private int level;
    private int price;
    private int priceIncrement;
    private int productionRate; //temps de pause du thread.
    private int goldStorage;
    private int maxGoldStorage;
    static int maxLevel;

    private Thread tGoldMine;


    public GoldMine(Map map, int price, int priceIncrement, int productionRate, int maxGoldStorage) {
        position = new Position(map);
        this.price = price;
        this.priceIncrement = priceIncrement;
        this.productionRate = productionRate; // en milliseconde.
        this.maxGoldStorage = maxGoldStorage;
        level = 1;
        goldStorage = 0;
        maxLevel = 5;
        //Initialisation du thread.
        tGoldMine = new Thread(this);
        tGoldMine.start();
    }


    //******Increasers*******

    private void increaseMaxGoldStorage(int increment){
        maxGoldStorage += increment;
    }

    /**
     * Prend comme argument la réduction du temps nécessaire à la production d'une quantité d'or.
     * Au plus l'argument (en milliseconde) est important, au plus la production d'or sera importante.
     * */
    public void increaseProductionRate(int decrement) {
        productionRate -= decrement;
    }

    //******Gestion de l'or*******

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

    //*******Passage de niveau*******

    public boolean canBeLeveledUp() {
        return level < maxLevel;
    }

    public int getCost(){
        return price;
    }

    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            price += priceIncrement;
            this.increaseProductionRate(10);
            this.increaseMaxGoldStorage(50);
        }
    }

    //******Autres*******
    @Override
    public void run(){
        try {
            while(true){
                produceGold();
                Thread.sleep(productionRate);
                System.out.println(toString());
            }
        }catch (Exception e){}
    }

    public void updateDrawing(){}//TODO : implémenter la représentation en JavaFX.

    public Position getPos(){
        return position;
    }

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
