package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Bullet;

public class StrongAttackTower extends Tower {
    private Bullet bullet;

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
        if (canBeLeveledUp()) {
            super.levelUp();
            damageDeal++;
        }
    }

    @Override
    public void attack(){
        for (NPC target: targets){
            target.hit(bullet);
        }
    }
}

