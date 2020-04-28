package towerdefense.game.towers;

import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Bullet;

public abstract class GenericTower extends Tower{
    protected Bullet bullet;

    public GenericTower(){
        super();
        bullet = new Bullet(damageDeal);
    }


    //******Passage de niveau******
    @Override
    public void levelUp() {
        if (canBeLeveledUp()) {
            super.levelUp();
        }
    }

    @Override
    public void attack(){
        for (NPC target: targets){
            target.hit(bullet);
        }
    }
}
