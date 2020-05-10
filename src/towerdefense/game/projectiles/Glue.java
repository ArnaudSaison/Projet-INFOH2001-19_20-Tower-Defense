package towerdefense.game.projectiles;

import towerdefense.game.Hittable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.view.projectile.ArrowView;

public class Glue extends Projectile {
    private static final String graphicsPath = "towers/glue_tower/projectile.png";
    private static final double proportion = 1.0 / 5.0;

    public Glue(Map map, GameModel gameModel, int damage, double velocity, Position initialPosition, NPC target) {
        super(map, gameModel, damage, velocity, initialPosition, target);
    }

    @Override
    public void doDamage(Hittable target) {
        target.hit(this);
    }

    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    @Override
    public void initDrawing() {
        synchronized (syncKeyDrawing) {
            view = new ArrowView(position, direction, map, graphicsPath, proportion);
        }
    }
}
