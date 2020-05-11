package towerdefense.view.projectile;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.view.ElementView;

public class ArrowView extends ElementView {
    private Position direction;

    public ArrowView(Position position, Position direction, Map map, String graphicsFileName, double proportion) {
        super(position, map, graphicsFileName, proportion);

        this.direction = direction;

        setOnMouseClicked(mouseEvent -> {
            System.out.println("arrow going in direction " + direction + " with angle of " + direction.getAngle() + " degrees and actual rotation is set to " + (imageView.getRotate() + 45));
        });

        imageView.setRotationAxis(Rotate.Z_AXIS);
        updateRotation();

//        setStyle("-fx-border-color: magenta; -fx-border-width: 1;");
    }

    @Override
    public void update() {
        super.update();
        updateRotation();
    }

    public void updateRotation() {
        imageView.setRotate(-(direction.getAngle() - 45)); // -45 car c'est l'orientation par défaut de l'asset
    }
}
