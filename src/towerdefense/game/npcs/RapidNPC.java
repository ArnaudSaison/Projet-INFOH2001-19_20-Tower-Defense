package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public class RapidNPC extends NPC {
    public RapidNPC(Map map, GameModel gameModel, int speed) {
        Position position = new Position(map);
        super.gameModel =gameModel;
        super.speed = speed;
    }
}

