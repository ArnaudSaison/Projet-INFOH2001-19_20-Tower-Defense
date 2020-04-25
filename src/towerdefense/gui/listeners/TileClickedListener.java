package towerdefense.gui.listeners;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import towerdefense.game.map.Tile;

public class TileClickedListener implements EventHandler<MouseEvent> {
    private final Tile tile;

    public TileClickedListener(Tile tile){
        this.tile = tile;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
            System.out.println(tile);
        }
    }
}
