package towerdefense.game.map;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PathTile extends Tile {
    enum Connector {TOP, BOTTOM, RIGHT, LEFT}
    private ArrayList<Connector> connect;

    // ***** Constructeur *****
    public PathTile(int x, int y, double tileMetricWidth){
        super(x, y, tileMetricWidth);
        this.getTileShape().getStyleClass().addAll("path-tile", "cannot-be-built-on");

        boolean[] connect = {};
        isBlocked = true;
    }

    public void addConnection(Connector co) {
        this.connect.add(co);
    }

    public void removeConnection(Connector co) {
        this.connect.remove(co);
    }

    public void updateConnection(){
    }
}
