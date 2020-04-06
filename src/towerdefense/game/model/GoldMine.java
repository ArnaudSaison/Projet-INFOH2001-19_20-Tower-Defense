package towerdefense.game.model;

import java.awt.geom.Point2D;

public class GoldMine {
    private Point2D.Double position;
    private int level;
    private int price;
    private int productionRate;
    private int goldStorage;
    private int maxGoldStorage;
    //private int health; (optionnel)

public GoldMine(Point2D.Double position, int level,int price, int productionRate, int goldStorage, int maxGoldStorage){
    this.position = position;
    this.level = level;
    this.price = price;
    this.productionRate = productionRate;
    this.goldStorage = goldStorage;
    this.maxGoldStorage = maxGoldStorage;
}

//public String toString(){}

public int getLevel(){return level;}

public int getPrice(){return price;}

public int getProductionRate(){return productionRate;}

public void increaseMaxGoldStorage(){
    maxGoldStorage++;
}

//public void storeGold(){}

}
