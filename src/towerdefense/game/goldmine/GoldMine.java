package towerdefense.game.goldmine;

import towerdefense.game.map.Position;
import towerdefense.game.interfaces.*;

public class GoldMine implements ProducesGold, Buyable, Upgradable, Placeable, Drawable {
    private Position position;
    private int level;
    private int price;
    private int productionRate;
    private int goldStorage;
    private int maxGoldStorage;
    static int maxLevel;
    //static int health = 1000; (optionnel)


    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

    public GoldMine() {
        Position position = new Position();
        level = 1;
        price = 200;
        productionRate = 10;
        goldStorage = 0;
        maxGoldStorage = 200;
        maxLevel = 5;
    }

    //*********Getteurs***********

    public int getLevel() {
        return level;
    }

    public int getPrice() {
        return price;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public Position getPos(){
        return position;
    }

    //******Increasers*******

    private void increaseMaxGoldStorage(int increment){
        maxGoldStorage += increment;
    }

    private void increasePrice(int increment){
        price += increment;
    }

    public void increaseProductionRate(int increment) {
        productionRate += increment;
    }

    //******Gestion de l'or*******

    private void storeGold(){
        if (goldStorage < maxGoldStorage){
            goldStorage += productionRate;
        }
    }

    //public int produceGold(){}  Je ne vois pas à quoi cette méthode servirait.

    public int retrieveGold(){
        int res = (int) Math.floor(0.5*price);
        return res;
    }

    //*******Passage niveau*******

    public boolean canBeLeveledUp() {
        if (level < maxLevel) {
            return true;
        } else {
            return false;
        }
    }

    public int getNextUpgradePrice(){return price+200;}

    public void levelUp(GoldMine mine) {
        if (mine.canBeLeveledUp()) {
            level++;
            maxGoldStorage += 50;
            mine.increaseProductionRate(10);
            mine.increasePrice(5 * level);
        }
    }

    //******Autres*******
    public void run(){}

    public void updateDrawing(){}

    public String toSring(){
        return "Mine d'or :\n - position: " + position + "\n" +
                "- level: " + level + "\n"+
                "- maxLevel: " + maxLevel + "\n"+
                "- maxGoldStorage: " + maxGoldStorage + "\n"+
                "- goldStorage: " + goldStorage + "\n"+
                "- productionRate: " + productionRate + "\n"+
                "- price: " + price + ".";
    }
}

