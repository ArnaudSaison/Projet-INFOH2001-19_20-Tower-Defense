package towerdefense.game;

import towerdefense.game.map.Position;
import towerdefense.game.projectiles.Arrow;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;

public interface Hittable {
    public void hit(Shell shell);
    public void hit(Arrow arrow);
    public void hit(Glue glue);
    public Position getPos();
}
