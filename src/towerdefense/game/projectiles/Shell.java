package towerdefense.game.projectiles;

import towerdefense.game.Hittable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;

public class Shell extends Projectile {
    private int radius;

    public Shell(Map map, Position initialPosition, GameModel gameModel, int damage, int radius){
        super(map, initialPosition, gameModel, damage);
        super.velocity = 1;
        this.radius = radius;
    }

    /**Fait exploser l'obus et touche tous les ennemis à portée*/
    public void doZoneDamage(){
        for(Hittable hittable : super.gameModel.getHittables()){
            if(position.getDistance(hittable.getPos()) <= radius){
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
