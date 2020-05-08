package towerdefense.game.projectiles;

import towerdefense.game.Hittable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;

public class Shell extends Projectile {
    private int radius;

    public Shell(Map map, GameModel gameModel, int damage, int velocity, Position initialPosition, NPC target, int radius){
        super(map, gameModel, damage, velocity, initialPosition, target);
        this.radius = radius;
    }

    /**Fait exploser l'obus et touche tous les ennemis à portée*/
    public void doZoneDamage(){
        for(Hittable hittable : super.gameModel.getHittables()){
            if(position.getDistance(hittable.getPosition()) <= radius){
                hittable.hit(this);
            }
        }
    }

    @Override
    public void doDamage(NPC target){
        target.hit(this);
        doZoneDamage(); //le npc va prendre deux fois dans la tronche mais c'est pour plus de réalisme.
    }
}
