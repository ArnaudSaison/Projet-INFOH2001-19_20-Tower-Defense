package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public class SuperHealthNPC extends NPC {
    public SuperHealthNPC(Map map, GameModel gameModel, int health) {
        Position position = new Position(map);
        super.gameModel =gameModel;
        super.health = health;
    }
}
