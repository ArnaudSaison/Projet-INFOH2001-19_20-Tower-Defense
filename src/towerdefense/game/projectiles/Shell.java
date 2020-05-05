package towerdefense.game.projectiles;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;

public class Shell extends Projectile {
    private int damage;
    private Thread tShell;

    public Shell(Map map, GameModel gameModel, int damage){
        super(map, gameModel, damage);
    }

    //Avoir un observer Pattern à peu près propre/

    //todo: dégat de zone. Notifier tous :si vous êtes dans ma zone alors dégats.
    // Obsever = hittable
    // notifyHitZone(int position, int radius) appelé sur tous les observer
}
