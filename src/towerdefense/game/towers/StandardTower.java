package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Arrow;

import java.util.ArrayList;

public class StandardTower extends Tower{
    private String type;
    private Arrow arrow;

    public StandardTower(Map map, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe, String type){
        super(map, gameModel,towerSpe);
        this.type = type;
        arrow = new Arrow(damageDeal);
    }

    @Override
    public void attack(){
        super.attack();
        for (NPC target : super.targets){
            target.hit(arrow);
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