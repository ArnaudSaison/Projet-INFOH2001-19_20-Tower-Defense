package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Glue;

import java.util.ArrayList;

public class GlueTower extends Tower {
    public GlueTower(Map map, Position pos, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe){
        super(map, pos, gameModel, towerSpe);
        this.graphicsName = "glue_tower/";
    }

    @Override
    public void attack(){
        super.attack();
        for (NPC target : super.targets){
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