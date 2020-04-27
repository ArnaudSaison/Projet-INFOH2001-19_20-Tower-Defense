package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class LongRangeTower extends Tower{

    public LongRangeTower(Map map,int range, int price){
        Position position = new Position(map);
        super.range = range;
        super.price = price;
    }

    //******Passage de niveau******
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            price = +priceIncrement;
            range++;
        }
    }
}

