package towerdefense.game.map;

import javafx.scene.paint.Color;

public class PathTile extends Tile {

    // ***** Constructeur *****
    public PathTile(int x, int y, double tileMetricWidth){
        super(x, y, tileMetricWidth);
        this.getTileShape().getStyleClass().addAll("path-tile", "cannot-be-built-on");
    }
}
