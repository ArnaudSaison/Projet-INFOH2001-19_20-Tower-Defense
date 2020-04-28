package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class LongRangeTower extends GenericTower{

    public LongRangeTower(Map map,int range, int price){
        super();
        super.position = new Position(map);
        super.range = range;
        super.price = price;
    }

    //******Passage de niveau******
    @Override
    public void levelUp() {
        super.levelUp();
        range++;
    }
    @Override
    public String toString(){
        return (getClass().getName() + "\n") + super.toString();
    }
}

