package towerdefense.game.npcs;

import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public abstract class GenericNPC extends NPC {
    public GenericNPC() {
        super();
    }

    @Override
    public void stick(Glue glue){
        speed = speed/glue.getDamage();
    }

    @Override
    public void explode(Shell shell){
        decreaseHealth(shell.getDamage());
    }

    @Override
    public void injure(Bullet bullet){
        decreaseHealth(bullet.getDamage());
    }

}