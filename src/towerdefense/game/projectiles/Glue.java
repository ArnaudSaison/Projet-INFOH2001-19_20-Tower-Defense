package towerdefense.game.projectiles;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;

public class Glue extends Projectile{

    public Glue(Map map, Position initialPosition, GameModel gameModel, int damage){
        super(map,initialPosition, gameModel, damage);
        super.velocity = 10;
    }

    @Override
    public void doDamage(NPC target){
        target.hit(this);
    }
}
