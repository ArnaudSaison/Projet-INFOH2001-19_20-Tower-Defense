package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Bullet;

public class GenericTower extends Tower{
    private Bullet bullet;

    public GenericTower(Map map){
        super();
        super.position = new Position(map);
        bullet = new Bullet(damageDeal);
    }


    //******Passage de niveau******
    @Override
    public void levelUp() {
        if (canBeLeveledUp()) {
            super.levelUp();
            range++;
            damageDeal++;
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
