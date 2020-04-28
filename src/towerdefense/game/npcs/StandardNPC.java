package towerdefense.game.npcs;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public class StandardNPC extends GenericNPC {
    public StandardNPC(Map map, GameModel gameModel) {
        super();
        super.gameModel = gameModel;
        super.position = new Position(map);
    }

    @Override
    public String toString(){
        return super.toString() + ("\n"+ getClass().getName() +"\n.");
    }
}