package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Glue;

public class GlueTower extends Tower {
    private Glue glue;

    public GlueTower(Map map,int maxTargetNumber,int speedDivider, int price) {
        super();
        super.position = new Position(map);
        super.maxTargetNumber = maxTargetNumber;
        super.price = price;
        super.damageDeal= speedDivider;
        glue = new Glue(damageDeal);
    }

    //******Passage de niveau******
    @Override
    public void levelUp() {
        if (canBeLeveledUp()) {
            super.levelUp();
            maxTargetNumber++;
        }
    }
    @Override
    public void attack(){
        for (NPC target: targets){
            target.hit(glue);
        }
    }
}