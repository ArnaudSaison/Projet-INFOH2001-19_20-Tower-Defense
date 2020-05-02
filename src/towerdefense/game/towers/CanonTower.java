package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Shell;

public class CanonTower extends Tower {
    private Shell shell;

    public CanonTower(Map map, GameModel gameModel, int range, int fireRate, int damageDeal, int maxTargetNumber){
        super(map, gameModel, range, fireRate, damageDeal, maxTargetNumber);
        shell = new Shell(damageDeal);
    }

    @Override
    public void hit(){
        super.hit();
        for (NPC target : super.targets){
            target.hit(shell);
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
