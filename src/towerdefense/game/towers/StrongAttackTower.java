package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.projectiles.Bullet;

public class StrongAttackTower extends GenericTower {

    public StrongAttackTower(Map map, int damageDeal, int price){
        super();
        super.position = new Position(map);
        super.damageDeal = damageDeal;
        super.price = price;
        bullet = new Bullet(damageDeal);
    }

    //******Passage de niveau******
    @Override
    public void levelUp() {
        super.levelUp();
        damageDeal++;
    }

    @Override
    public String toString(){
        return (getClass().getName() + "\n") + super.toString();
    }
}

