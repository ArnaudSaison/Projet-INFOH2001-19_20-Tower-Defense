package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public class SuperHealthNPC extends GenericNPC {
    public SuperHealthNPC(Map map, GameModel gameModel, int health) {
        super();
        super.gameModel =gameModel;
        super.health = health;
        super.position = new Position(map);
    }
    @Override
    public String toString(){
        return super.toString() + ("\n"+ getClass().getName() +"\n.");
    }
}
