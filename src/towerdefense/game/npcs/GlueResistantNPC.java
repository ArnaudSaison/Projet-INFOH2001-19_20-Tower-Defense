package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Arrow;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public class GlueResistantNPC extends NPC {

    public GlueResistantNPC(Map map, GameModel gameModel, int health, int speed, int goldLoot, int scoreLoot) {
        super(map, gameModel, health, speed, goldLoot, scoreLoot);
    }

    @Override
    //Ne fait rien ici car ce NPC est resistant à la colle.
    public void stick(Glue glue){}

    @Override
    public void explode(Shell shell){
        decreaseHealth(shell.getDamage());
    }

    @Override
    public void injure(Arrow arrow){
        decreaseHealth(arrow.getDamage());
    }

    @Override
    public String toString(){
        return super.toString() + ("\n"+ getClass().getName() +"\n.");
    }
}