package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Glue;

public class GlueTower extends Tower {
    private Glue glue;

    public GlueTower(Map map, GameModel gameModel,int price, int range, int fireRate, int damageDeal, int maxTargetNumber){
        super(map, gameModel,price, range, fireRate, damageDeal, maxTargetNumber);
        glue = new Glue(damageDeal);
    }

    @Override
    public void hit(){
        super.hit();
        for (NPC target : super.targets){
            target.hit(glue);
        }
    }

    @Override
    public void levelUp() {
        super.levelUp();
    }

    @Override
    public String toString(){
        return (super.toString() + "\n" + getClass().getName() + ".");
    }
}