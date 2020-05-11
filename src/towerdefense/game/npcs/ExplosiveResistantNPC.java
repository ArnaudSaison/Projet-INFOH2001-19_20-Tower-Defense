package towerdefense.game.npcs;

import towerdefense.game.map.GatePathTile;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Arrow;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;
import towerdefense.view.npc.NPCView;

public class ExplosiveResistantNPC extends NPC {

    public ExplosiveResistantNPC(Map map, GameModel gameModel, int health, int speed, int goldLoot, int scoreLoot, GatePathTile gatePathTile) {
        super(map, gameModel, health, speed, goldLoot, scoreLoot, gatePathTile);
    }

    @Override
    public void stick(Glue glue) {
        if (speed == initialSpeed) {
            speed = speed / glue.getDamage();
        }
    }

    //Ne fait rien ici car ce NPC est résistant aux explosifs.
    @Override
    public void injure(Shell shell) {
    }

    @Override
    public void pierce(Arrow arrow) {
        decreaseHealth(arrow.getDamage());
    }

    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    @Override
    public void initDrawing() {
        synchronized (syncKeyDrawing) {
            npcView = new NPCView(this, map, "armor");
        }
    }

    @Override
    public String toString() {
        return super.toString() + ("\n" + getClass().getName() + "\n.");
    }
}
