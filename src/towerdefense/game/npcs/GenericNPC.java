package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public class GenericNPC extends NPC {
    public GenericNPC(Map map, GameModel gameModel) {
        super();
        super.gameModel = gameModel;
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