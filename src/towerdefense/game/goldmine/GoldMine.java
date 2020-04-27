package towerdefense.game.goldmine;

import towerdefense.game.*;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class GoldMine implements ProducesGold, Buyable, Upgradable, Placeable, Drawable {
    private Position position;
    private int level;
    private int price;
    private int priceIncrement;
    private int productionRate;
    private int goldStorage;
    private int maxGoldStorage;
    static int maxLevel;


    //NOTE: les valeurs mises ici le sont à titre d'exemple, à modifier si besoin.

    public GoldMine(Map map, int price, int priceIncrement, int productionRate, int maxGoldStorage) {
        Position position = new Position(map);
        this.price = price;
        this.priceIncrement = priceIncrement;
        this.productionRate = productionRate;
        this.maxGoldStorage = maxGoldStorage;
        level = 1;
        goldStorage = 0;
        maxLevel = 5;
    }


    //******Increasers*******

    private void increaseMaxGoldStorage(int increment){
        maxGoldStorage += increment;
    }

    public void increaseProductionRate(int increment) {
        productionRate += increment;
    }

    //******Gestion de l'or*******

    public void produceGold(){
        if (goldStorage < maxGoldStorage){
            goldStorage += productionRate;
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

    //****** Getteurs & setteurs ******

    public int getLevel(){
        return level;
    }

    public int getPrice(){
        return price;
    }

    public int getProductionRate(){
        return level;
    }

    public int getGoldStorage(){
        return goldStorage;
    }

    public int getMaxGoldStorage(){
        return maxGoldStorage;
    }

    public int getMaxLevel(){
        return maxLevel;
    }

    //******Autres*******

    public void updateDrawing(){}//TODO : implémenter la représentation en JavaFX.

    public Position getPos(){
        return position;
    }
    public String toSringGoldMine(){
        return "Mine d'or :\n - position: " + position + "\n" +
                "- level: " + level + "\n"+
                "- maxLevel: " + maxLevel + "\n"+
                "- maxGoldStorage: " + maxGoldStorage + "\n"+
                "- goldStorage: " + goldStorage + "\n"+
                "- productionRate: " + productionRate + "\n"+
                "- price: " + price + ".";
    }
}
