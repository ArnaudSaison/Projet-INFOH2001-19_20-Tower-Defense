package towerdefense.view.editor;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.map.Position;
import towerdefense.game.map.Tile;

public class PlaceTileListener implements EventHandler<MouseEvent> {
    private Map map;
    private MapFactory.TileType tile;

    public PlaceTileListener(Map map, MapFactory.TileType tileType) {
        this.map = map;
        this.tile = tileType;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown()) {

        }
    }
}
