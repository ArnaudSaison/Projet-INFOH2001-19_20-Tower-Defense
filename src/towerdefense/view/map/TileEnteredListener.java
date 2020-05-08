package towerdefense.view.map;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;
import towerdefense.view.shop.TemporaryItem;

public class TileEnteredListener implements EventHandler<MouseEvent> {
    private Tile tile;
    private MapView mapView;

    public TileEnteredListener(Tile tile, Map map) {
        this.tile = tile;
        this.mapView = (MapView) map.getDrawing(); // Le Drawing d'une Map sera toujours une MapView
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
            if (mapView.tempElementPresent()) {
                mapView.getTempElement().setPosition(tile.getPosition());
            }
        }
    }
}
