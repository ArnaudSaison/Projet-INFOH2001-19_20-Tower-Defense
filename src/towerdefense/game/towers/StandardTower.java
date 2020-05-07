package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Arrow;

import java.util.ArrayList;

public class StandardTower extends Tower{
    private String type;

    public StandardTower(Map map, Position pos, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe, String type){
        super(map, pos, gameModel, towerSpe);
        this.type = type;
        this.graphicsName = "archer_tower/";
    }

    @Override
    public void attack(){
        super.attack();
        for (NPC target : super.targets){
            new Arrow(map, super.position, NPC target, gameModel, damageDeal);
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