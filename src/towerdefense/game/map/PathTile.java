package towerdefense.game.map;

import javafx.scene.paint.Color;

public class PathTile extends Tile {

    // ***** Constructeur *****
    public PathTile(int x, int y, double tileMetricWidth){
        super(x, y, tileMetricWidth);
        this.setShapeFill(Color.web("#e6e6e6"));
    }
}
