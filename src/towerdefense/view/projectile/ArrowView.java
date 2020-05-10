package towerdefense.view.projectile;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.view.ElementView;

public class ArrowView extends ElementView {
    private Position direction;

    public ArrowView(Position position, Position direction, Map map, String graphicsFileName, double proportion) {
        super(position, map, graphicsFileName, proportion);

        this.direction = direction;
    }

    @Override
    public void update() {
        super.update();
        updateRotation();
    }

    public void updateRotation() {
        imageView.setRotate(direction.getAngle() - 45); // -45 car c'est l'orientation par défaut de l'asset
    }
}
