package towerdefense.game.map;

import javafx.scene.paint.Color;

public class ObstacleTile extends Tile {

    // ***** Constructeur *****
    public ObstacleTile(int x, int y, double tileMetricWidth){
        super(x, y, tileMetricWidth);
        this.getTileShape().getStyleClass().addAll("obstacle-tile", "cannot-be-built-on");
        isBlocked = true;
    }
}
