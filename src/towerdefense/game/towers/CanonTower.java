package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Shop;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Shell;

import java.util.ArrayList;

public class CanonTower extends Tower {
    private static final Shop.ShopCases ID = Shop.ShopCases.CANON_TOWER;

    public CanonTower(Map map, Position pos, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe){
        super(map, pos, gameModel, towerSpe, ID);
        this.graphicsName = Shop.getIconPath(ID);
    }

    @Override
    public void attack(){
        super.attack();
        for (NPC target : super.targets){
        }
    }

    public void levelUp() {
        super.levelUp();
    }

    public String toString(){
        return (super.toString() + "\n" + getClass().getName() + ".");
    }
}