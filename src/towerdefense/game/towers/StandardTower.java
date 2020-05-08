package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Shop;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Arrow;

import java.util.ArrayList;

public class StandardTower extends Tower{
    private static final Shop.ShopCases ID = Shop.ShopCases.STANDARD_TOWER;
    private final String type;

    public StandardTower(Map map, Position pos, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe, String type){
        super(map, pos, gameModel, towerSpe, ID);
        this.type = type;
        this.graphicsName = Shop.getIconPath(ID);
    }

    @Override
    public void attack(){
        super.attack();
        for (NPC target : super.targets){
//            new Arrow(map, super.position, NPC target, gameModel, damageDeal);
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