package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Shell;

public class CanonTower extends Tower {
    private Shell shell;

    public CanonTower(Map map, int maxTargetNumber, int damageDeal, int price) {
        super();
        super.position = new Position(map);
        super.maxTargetNumber = maxTargetNumber;
        super.price = price;
        shell = new Shell(damageDeal);
    }

    //*******Passage de niveau******
    @Override
    public void levelUp() {
        if (canBeLeveledUp()) {
            super.levelUp();
            maxTargetNumber++;
            damageDeal++;
        }
    }

    //********Attaque*********
    @Override
    public void attack(){
        for (NPC target: targets){
            target.hit(shell);
        }
    }
    @Override
    public String toString(){
        return (getClass().getName() + "\n") + super.toString();
    }
}
