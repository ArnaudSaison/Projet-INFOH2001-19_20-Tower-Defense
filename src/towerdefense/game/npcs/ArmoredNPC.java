package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public class ArmoredNPC extends NPC {
    public ArmoredNPC(Map map, GameModel gameModel, int goldLoot){
        Position position = new Position(map);
        super.gameModel = gameModel;
        super.goldLoot = goldLoot;
    }
}
