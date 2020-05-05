package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Glue;

import java.util.ArrayList;

public class GlueTower extends Tower {
    private Glue glue;

    public GlueTower(Map map, GameModel gameModel,ArrayList<ArrayList<Integer>> towerSpe){
        super(map, gameModel,towerSpe);
    }

    @Override
    public void attack(){
        super.attack();
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