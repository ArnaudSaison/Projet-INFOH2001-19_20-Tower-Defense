package towerdefense.game.towers;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Shop;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Arrow;
import towerdefense.game.projectiles.Glue;

import java.util.ArrayList;

public class GlueTower extends Tower {
    private static final Shop.ShopCases ID = Shop.ShopCases.GLUE_TOWER;

    public GlueTower(Map map, Position pos, GameModel gameModel, ArrayList<ArrayList<Integer>> towerSpe) {
        super(map, pos, gameModel, towerSpe, ID);
        this.graphicsName = Shop.getIconPath(ID);
    }

    @Override
    public void attack() {
        super.attack();
        for (NPC target : super.targets) {
            Glue glue = new Glue(map, gameModel, damageDeal, 2, range, super.position, target);
            gameModel.initializeElement(glue);
        }
    }

    public void levelUp() {
        super.levelUp();
    }

    @Override
    public String toString() {
        return (super.toString() + "\n" + getClass().getName() + ".");
    }

    @Override
    public Shop.ShopCases getID() {
        return ID;
    }
}