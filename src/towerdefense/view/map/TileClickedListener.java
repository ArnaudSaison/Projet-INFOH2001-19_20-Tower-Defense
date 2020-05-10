package towerdefense.view.map;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import towerdefense.game.map.Path;
import towerdefense.game.map.PathTile;
import towerdefense.game.map.Tile;

public class TileClickedListener implements EventHandler<MouseEvent> {
    private final Tile tile;

    public TileClickedListener(Tile tile) {
        this.tile = tile;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown()) {
            System.out.println(tile);

            if (tile instanceof PathTile) {
                System.out.println(((PathTile) tile).getConnections());
            }
            System.out.println("blocked: " + tile.getBlockedState());
        }
    }
}
