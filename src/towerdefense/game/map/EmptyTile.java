package towerdefense.game.map;

import javafx.scene.paint.Color;

public class EmptyTile extends Tile {

    // ***** Constructeur *****
    public EmptyTile(int x, int y, double tileMetricWidth){
        super(x, y, tileMetricWidth);
        this.setShapeFill(Color.web("#33cc33"));
    }
}
