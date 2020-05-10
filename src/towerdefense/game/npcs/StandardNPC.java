package towerdefense.game.npcs;

import towerdefense.game.map.GatePathTile;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Arrow;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;
import towerdefense.game.waves.WaveFactory;
import towerdefense.view.npc.NPCView;

public class StandardNPC extends NPC {
    private WaveFactory.NPCTypes type;

    public StandardNPC(Map map, GameModel gameModel, int health, int speed, int goldLoot, int healthLoot, GatePathTile gatePathTile, WaveFactory.NPCTypes type) {
        super(map, gameModel, health, speed, goldLoot, healthLoot, gatePathTile);
        this.type = type;
    }

    @Override
    public void stick(Glue glue) {
        if (speed == initialSpeed) {
            speed = speed / glue.getDamage();
        }
    }

    @Override
    public void injure(Shell shell) {
        decreaseHealth(shell.getDamage());
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
            npcView = new NPCView(this, map, "generic");
        }
    }

    @Override
    public String toString() {
        return super.toString() + ("\n" + getClass() + type + ".");
    }
}