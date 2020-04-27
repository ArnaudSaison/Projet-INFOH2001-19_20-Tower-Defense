package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class GlueTower extends Tower {


    public GlueTower(Map map,int maxTargetNumber,int speedDivider, int price) {
        Position position = new Position(map);
        super.maxTargetNumber = maxTargetNumber;
        super.price = price;
        super.damageDeal= speedDivider;
    }

    //******Passage de niveau******
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            price = +priceIncrement;
            maxTargetNumber++;
        }
    }
}