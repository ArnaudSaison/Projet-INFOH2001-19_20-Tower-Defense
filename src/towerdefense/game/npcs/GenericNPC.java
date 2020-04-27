package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public class GenericNPC extends NPC {
    public GenericNPC(Map map, GameModel gameModel) {
        Position position = new Position(map);
        super.gameModel = gameModel;
    }
}