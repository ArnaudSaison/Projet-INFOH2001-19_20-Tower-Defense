package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Shell;

import java.util.ArrayList;

public class CanonTower extends Tower {
    private Shell shell;

    public CanonTower(Map map, GameModel gameModel,ArrayList<ArrayList<Integer>> towerSpe){
        super(map, gameModel,towerSpe);
    }

    @Override
    public void attack(){
        super.attack();
        shell = new Shell(damageDeal);
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