package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public class GlueResistantNPC extends NPC {
    public GlueResistantNPC(Map map, GameModel gameModel, int goldLoot) {
        super();
        super.gameModel = gameModel;
        super.goldLoot = goldLoot;
        super.position = new Position(map);
    }

    public boolean isGlueResistant() {
        return true;
    }

    @Override
    public void stick(Glue glue){}

    @Override
    public void explode(Shell shell){
        decreaseHealth(shell.getDamage());
    }

    @Override
    public void injure(Bullet bullet){
        decreaseHealth(bullet.getDamage());
    }
}