package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Bullet;

public class StandardTower extends Tower{
    private String type;
    private Bullet bullet;

    public StandardTower(Map map, GameModel gameModel, int range, int fireRate, int damageDeal, int maxTargetNumber, String type){
        super(map, gameModel, range, fireRate, damageDeal, maxTargetNumber);
        this.type = type;
        bullet = new Bullet(damageDeal);
    }

    @Override
    public void hit(){
        super.hit();
        for (NPC target : super.targets){
            target.hit(bullet);
        }
    }

    @Override
    public void levelUp() {
        super.levelUp();
    }

    @Override
    public String toString(){
        return (super.toString() + "\n" + getClass() + type + ".");
    }
}
