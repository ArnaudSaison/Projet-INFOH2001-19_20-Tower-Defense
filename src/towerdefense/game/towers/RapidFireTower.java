package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class RapidFireTower extends Tower {

    public RapidFireTower(Map map, int fireRate, int price){
        Position position = new Position(map);
        super.fireRate = fireRate;
        super.price = price;
    }

    //******Passage de niveau******
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            price = +priceIncrement;
            fireRate++;
        }
    }
}