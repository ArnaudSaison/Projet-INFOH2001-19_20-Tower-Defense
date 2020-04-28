package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public class RapidNPC extends GenericNPC {
    public RapidNPC(Map map, GameModel gameModel, int speed) {
        super();
        super.gameModel = gameModel;
        super.speed = speed;
        super.position = new Position(map);
    }

    @Override
    public String toString() {
        return super.toString() + ("\n" + getClass().getName() + "\n.");
    }
}