package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public class StandardTower extends GenericTower{

    public StandardTower(Map map){
        super();
        super.position = new Position(map);
    }

    //******Passage de niveau******
    @Override
    public void levelUp() {
        super.levelUp();
        range++;
        damageDeal++;
        fireRate++;
    }
    @Override
    public String toString(){
        return (getClass().getName() + "\n") + super.toString();
    }
}
