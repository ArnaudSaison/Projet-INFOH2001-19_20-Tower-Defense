package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public class SuperHealthNPC extends NPC {
    public SuperHealthNPC(Map map, GameModel gameModel, int health) {
        super();
        super.gameModel =gameModel;
        super.health = health;
        super.position = new Position(map);
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
