package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;


public class CanonTower extends Tower {
    public CanonTower(Map map, int maxTargetNumber, int damageDeal, int price) {
        Position position = new Position(map);
        super.maxTargetNumber = maxTargetNumber;
        super.price = price;
        super.damageDeal = damageDeal;
    }


    //*******Passage de niveau******
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            price += priceIncrement;
            maxTargetNumber++;
            damageDeal++;
        }
    }
}
