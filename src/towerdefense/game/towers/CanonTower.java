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
        int shellRadius = 5;
        shell = new Shell(map, super.position, gameModel, super.damageDeal, shellRadius);
    }

    @Override
    public void attack(){
        super.attack();
        for (NPC target : super.targets){
            shell.initialize(target);
        }
    }

    public void levelUp() {
        super.levelUp();
    }

    public String toString(){
        return (super.toString() + "\n" + getClass().getName() + ".");
    }
}