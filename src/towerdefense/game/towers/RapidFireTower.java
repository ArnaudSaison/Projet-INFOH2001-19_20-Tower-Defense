package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.projectiles.Bullet;

public class RapidFireTower extends GenericTower {

    public RapidFireTower(Map map, int fireRate, int price){
        super();
        super.position = new Position(map);
        super.fireRate = fireRate;
        super.price = price;
        bullet = new Bullet(damageDeal);
    }

    //******Passage de niveau******
    @Override
    public void levelUp() {
        super.levelUp();
        fireRate++;
    }
    @Override
    public String toString(){
        return (getClass().getName() + "\n") + super.toString();
    }
}