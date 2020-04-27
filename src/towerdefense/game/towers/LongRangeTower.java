package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Bullet;

public class LongRangeTower extends Tower{
    private Bullet bullet;

    public LongRangeTower(Map map,int range, int price){
        super();
        super.position = new Position(map);
        super.range = range;
        super.price = price;
        bullet = new Bullet(damageDeal);
    }

    //******Passage de niveau******
    @Override
    public void levelUp() {
        if (canBeLeveledUp()) {
            super.levelUp();
            range++;
        }
    }

    @Override
    public void attack(){
        for (NPC target: targets){
            target.hit(bullet);
        }
    }
}

