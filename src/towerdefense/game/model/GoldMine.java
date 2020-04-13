package towerdefense.game.model;

import towerdefense.game.map.Position;
import towerdefense.game.model.interfaces.*;

public class GoldMine implements ProducesGold, Buyable, Upgradable, Placeable, Drawable {
    private Position position;
    private int level;
    private int price;
    private int productionRate;
    private int goldStorage;
    private int maxGoldStorage;
    private int maxLevel;
    //private int health; (optionnel)

    public GoldMine() {
        Position position = new Position();
        level = 1;
        price = 200;
        productionRate = 10;
        goldStorage = 0;
        maxGoldStorage = 200;
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

    //******Getstion de l'or*******

    private void storeGold(){}

    public void produceGold() {
        if (goldStorage <= maxGoldStorage) {
            goldStorage += gold * productionRate;
        }
    }

    public int retrieveGold(){}

    //*******Passage niveau*******

    public boolean canBeLevelUp(int maxLevel) {
        if (level < maxLevel) {
            return true;
        } else {
            return false;
        }
    }

    public int getNextUpgradePrice(){}

    public void levelUp(GoldMine mine) {
        if (mine.canBeLevelUp(10)) {
            level++;
            maxGoldStorage += 50;
            mine.increaseProductionRate(10);
            mine.increasePrice(5 * level);
        }
    }

    //******Autres*******
    public void run(){}

    public void updateDrawing(){}

    public String toSring(){}
}

