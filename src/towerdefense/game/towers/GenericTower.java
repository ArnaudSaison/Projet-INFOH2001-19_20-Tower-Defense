package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class GenericTower extends Tower{

    public GenericTower(Map map){
        Position position = new Position(map);
    }


    //******Passage de niveau******
    public void levelUp() {
        if (canBeLeveledUp()) {
            level++;
            price = +priceIncrement;
            range++;
            damageDeal++;
            fireRate++;
        }
    }
}
