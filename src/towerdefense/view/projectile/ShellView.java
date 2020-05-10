package towerdefense.view.projectile;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.view.ElementView;

public class ShellView extends ElementView {
    public ShellView(Position position, Map map, String graphicsFileName, double proportion) {
        super(position, map, graphicsFileName, proportion);
    }
}
