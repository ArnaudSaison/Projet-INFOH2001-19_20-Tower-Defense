package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public class ExplosiveResistantNPC extends NPC {
    public ExplosiveResistantNPC(Map map, GameModel gameModel, int goldLoot){
        super();
        super.gameModel = gameModel;
        super.goldLoot = goldLoot;
        super.position = new Position(map);
    }

    @Override
    public void stick(Glue glue){
        speed = speed/glue.getDamage();
    }

    @Override
    public void explode(Shell shell){}

    @Override
    public void injure(Bullet bullet){
        decreaseHealth(bullet.getDamage());
    }

    @Override
    public String toString(){
        return super.toString() + ("\n"+ getClass().getName() +"\n.");
    }
}