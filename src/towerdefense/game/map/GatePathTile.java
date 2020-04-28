package towerdefense.game.map;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.InputStream;

public class GatePathTile extends PathTile {
    private double zoomFact = 1.0 / 2.0;
    private ImageView imageView;

    // ==================== Constructeur ====================
    public GatePathTile(int x, int y, double tileMetricWidth) {
        super(x, y, tileMetricWidth);
        imageView = new ImageView();
    }

    // ==================== JavaFX ====================
    public void initArrow(PathTile.Connections connection) {
        InputStream input = this.getClass().getResourceAsStream("../../../resources/graphics/arrow.png");
        Image image = new Image(input, 100, 100, true, false);

        imageView.setImage(image);
        imageView.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(true);

        ((StackPane) getTileShapeContainer()).getChildren().add(imageView);

        if (connection == Connections.RIGHT) {
            imageView.setRotate(0);
            ((StackPane) getTileShapeContainer()).setAlignment(imageView, Pos.CENTER_LEFT);

        } else if (connection == Connections.BOTTOM) {
            imageView.setRotate(90);
            ((StackPane) getTileShapeContainer()).setAlignment(imageView, Pos.TOP_CENTER);

        } else if (connection == Connections.LEFT) {
            imageView.setRotate(180);
            ((StackPane) getTileShapeContainer()).setAlignment(imageView, Pos.CENTER_RIGHT);

        } else if (connection == Connections.TOP) {
            imageView.setRotate(270);
            ((StackPane) getTileShapeContainer()).setAlignment(imageView, Pos.BOTTOM_CENTER);
        }
    }


    @Override
    public void updateDrawing() {
        super.updateDrawing();
        imageView.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
    }
}
