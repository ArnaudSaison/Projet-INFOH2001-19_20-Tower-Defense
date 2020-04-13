package towerdefense.game.model;

import towerdefense.game.map.Position;

public class GoldMine extends Game implements  ProducesGold, Buyable, UpgradeLevel {
    private Position position;
    private int level;
    private int price;
    private int productionRate;
    private int goldStorage;
    private int maxGoldStorage;
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

    public int getLevel() {return level;}

    public int getPrice() {return price;}

    public int getProductionRate() {return productionRate;}

    public int getMaxGoldStorage() {return maxGoldStorage;}

    public int getGoldStorage() {return goldStorage;}

    //******Settteurs*******

    public void setPosition(Position position){this.position = position;}

    //*********Autres***********

    public void increaseProductionRate(int increment) {productionRate += increment;}

    public void increasePrice(int increment) {price += increment;}

    public void produceGold() {
        if (goldStorage <= maxGoldStorage) {
            goldStorage += gold *productionRate;
        }
    }

    public boolean canBeLevelUp(int maxLevel) {
        if (level < maxLevel) {
            return true;
        } else {
            return false;
        }
    }

    public void levelUp(GoldMine mine) {
        if (mine.canBeLevelUp(10)) {
            level++;
            maxGoldStorage += 50;
            mine.increaseProductionRate(10);
            mine.increasePrice(5*level);
        }
    }

    @Override
    public void levelUp(Tower tower) {

    }

    //private void storeGold() {} ?
    //public String toString(){} ?
}

