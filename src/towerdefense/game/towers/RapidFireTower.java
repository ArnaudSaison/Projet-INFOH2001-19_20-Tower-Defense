package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Bullet;

public class RapidFireTower extends Tower {
    private Bullet bullet;

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
        if (canBeLeveledUp()) {
            super.levelUp();
            fireRate++;
        }
    }

    @Override
    public void attack(){
        for (NPC target: targets){
            target.hit(bullet);
        }
    }
}