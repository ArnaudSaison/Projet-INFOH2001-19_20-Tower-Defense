package towerdefense.game.projectiles;

import towerdefense.game.Hittable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.view.Printable;
import towerdefense.view.projectile.ArrowView;

public class Arrow extends Projectile {
    private static final String graphicsPath = "towers/archer_tower/projectile.png";
    private static final double proportion = 1.0 / 5.0;

    public Arrow(Map map, GameModel gameModel, int damage, int velocity, Position initialPosition, NPC target) {
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
