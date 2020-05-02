package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public class ExplosiveResistantNPC extends NPC {

    public ExplosiveResistantNPC(Map map, GameModel gameModel, int health, int speed, int goldLoot) {
        super(map, gameModel, health, speed, goldLoot);
    }

    @Override
    public void stick(Glue glue){
        speed = speed/glue.getDamage();
    }

    //Ne fait rien ici car ce NPC est résistant aux explosifs.
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
