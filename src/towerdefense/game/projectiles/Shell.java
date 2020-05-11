package towerdefense.game.projectiles;

import towerdefense.game.Hittable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.view.projectile.ArrowView;

public class Shell extends Projectile {
    private static final String graphicsPath = "towers/cannon_tower/projectile.png";
    private static final double proportion = 1.0 / 5.0;
    private int radius;

    public Shell(Map map, GameModel gameModel, int damage, double velocity, double range, Position initialPosition, NPC target, int radius) {
        super(map, gameModel, damage, velocity, range, initialPosition, target);
        this.radius = radius;
    }

    /**
     * Fait exploser l'obus et touche tous les ennemis à portée
     */
    public void doZoneDamage() {
        for (Hittable hittable : super.gameModel.getHittables()) {
            if (position.getDistance(hittable.getPosition()) <= radius) {
                hittable.hit(this);
                System.out.println("zone damage");
            }
        }
    }

    @Override
    public void doDamage(Hittable target) {
        target.hit(this);
        doZoneDamage(); //le npc va prendre deux fois dans la tronche mais c'est pour plus de réalisme.
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
