package towerdefense.game.map;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PathTile extends Tile {
    public enum Connections {TOP, BOTTOM, LEFT, RIGHT}

    protected boolean top;
    protected boolean bottom;
    protected boolean left;
    protected boolean right;

    // ***** Constructeur *****
    public PathTile(int x, int y, double tileMetricWidth) {
        super(x, y, tileMetricWidth);
        this.getTileShape().getStyleClass().addAll("path-tile", "cannot-be-built-on");

        top = false;
        bottom = false;
        left = false;
        right = false;
    }

    public void setConnection(Connections connection, boolean value) {
        switch (connection) {
            case TOP:
                top = value;
                break;
            case BOTTOM:
                bottom = value;
                break;
            case RIGHT:
                right = value;
                break;
            case LEFT:
                left = value;
                break;
        }
    }

    public void reinitializeConnections() {
        top = false;
        bottom = false;
        left = false;
        right = false;
    }

    public boolean getConnection(Connections connection) {
        boolean res = false;
        switch (connection) {
            case TOP:
                res = top;
                break;
            case BOTTOM:
                res = bottom;
                break;
            case RIGHT:
                res = right;
                break;
            case LEFT:
                res = left;
                break;
        }
        return res;
    }

    @Override
    public void updateDrawing() {
        super.updateDrawing();
    }

    public void updateConnection() {
    }
}
