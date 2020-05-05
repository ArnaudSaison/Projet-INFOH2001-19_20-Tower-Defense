package towerdefense.controller.map.selector;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MapClickedListener implements EventHandler<MouseEvent> {
    private MapSelectorController controller;
    private HBox mapElement;
    private String mapPath;

    public MapClickedListener(MapSelectorController controller, HBox mapElement, String mapPath){
        this.controller = controller;
        this.mapPath = mapPath;
        this.mapElement = mapElement;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        controller.setSelectedMapPath(mapPath, mapElement);
    }
}
