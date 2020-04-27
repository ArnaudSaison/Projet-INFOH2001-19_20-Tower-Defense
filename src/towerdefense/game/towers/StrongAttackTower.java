package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class StrongAttackTower extends Tower {

    public StrongAttackTower(Map map, int damageDeal, int price){
        Position position = new Position(map);
        super.damageDeal = damageDeal;
        super.price = price;
    }

    //******Passage de niveau******
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            price = +priceIncrement;
            damageDeal++;
        }
    }
}

