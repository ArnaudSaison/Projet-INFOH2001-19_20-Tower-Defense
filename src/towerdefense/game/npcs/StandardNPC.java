package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Bullet;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;
import towerdefense.game.waves.WaveFactory;

public class StandardNPC extends NPC {
    private WaveFactory.NPCTypes type;

    public StandardNPC(Map map, GameModel gameModel, int health, int speed, int goldLoot,int healthLoot, WaveFactory.NPCTypes type) {
        super(map, gameModel, health, speed, goldLoot, healthLoot);
        this.type=type;
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

    @Override
    public String toString(){
        return super.toString() + ("\n"+ getClass() + type +".");
    }
}