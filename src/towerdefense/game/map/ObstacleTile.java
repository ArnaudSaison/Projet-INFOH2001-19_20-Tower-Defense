package towerdefense.game.map;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class ObstacleTile extends Tile {
    private double zoomFact = 1;
    private ImageView imageView;

    // ***** Constructeur *****
    public ObstacleTile(int x, int y, double tileMetricWidth){
        super(x, y, tileMetricWidth);
        this.getTileShape().getStyleClass().addAll("obstacle-tile", "cannot-be-built-on");
        isBlocked = true;

        imageView = new ImageView();
//        initObstacle();
    }

    public void initObstacle() {
        InputStream input = this.getClass().getResourceAsStream("../../../resources/graphics/tree.png");
        Image image = new Image(input, 100, 100, true, false);

        imageView.setImage(image);
        imageView.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(true);

        ((StackPane) getTileShapeContainer()).getChildren().add(imageView);
        ((StackPane) getTileShapeContainer()).setAlignment(imageView, Pos.CENTER);
    }

    @Override
    public void updateDrawing() {
        super.updateDrawing();
        imageView.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
    }
}
