package towerdefense.view.editor;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import towerdefense.controller.map.editor.MapEditorController;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.map.TileType;
import towerdefense.view.map.MapView;

public class PlaceTileListener implements EventHandler<MouseEvent> {
    private Map map;
    private TileType type;
    private Node tile;
    private SideBarTilesSelector sideBarView;
    private MapEditorController controller;

    public PlaceTileListener(Map map, TileType type, Node tile, SideBarTilesSelector sideBarView, MapEditorController controller) {
        this.map = map;
        this.type = type;
        this.tile = tile;
        this.sideBarView = sideBarView;
        this.controller = controller;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (controller.getRunning()) {
            if (sideBarView.getSelectedState(tile)) {
                sideBarView.deselectItems();
                ((MapView) map.getDrawing()).removeTempElement();
            } else {
                sideBarView.setSelectedItem(tile); //graphiquement montrer que l'élément est sélectionné
                ((MapView) map.getDrawing()).setTempElement(new TemporaryTileView(type, map, sideBarView, controller));
            }
        }
    }
}
