package towerdefense.game.projectiles;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;

public class Arrow extends Projectile {

    public Arrow(Map map, GameModel gameModel, int damage, int velocity, Position initialPosition, NPC target){
    super(map, gameModel, damage, velocity, initialPosition, target);
    }


    @Override
    public void doDamage(NPC target){
        target.hit(this);
    }
}
