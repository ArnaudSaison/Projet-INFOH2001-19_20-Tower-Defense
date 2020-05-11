package towerdefense.view.editor;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.map.Position;
import towerdefense.game.map.Tile;
import towerdefense.game.model.Shop;
import towerdefense.view.map.MapView;
import towerdefense.view.shop.sideBarView;
import towerdefense.view.shop.TemporaryItemView;

public class PlaceTileListener implements EventHandler<MouseEvent> {
    private Map map;
    private Shop.ShopCases item;
    private Node tile;
    private Shop shop;
    private SideBarTilesSelector sideBarView;

    public ItemClicked(Map map, Shop.ShopCases item, Node tile, Shop shop, SideBarTilesSelector sideBarView) {
        this.map = map;
        this.item = item;
        this.tile = tile;
        this.shop = shop;
        this.sideBarView = sideBarView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (sideBarView.getSelectedState(tile)) {
            sideBarView.deselectItems();
            ((MapView) map.getDrawing()).removeTempElement();
        } else {
            sideBarView.setSelectedItem(tile); //graphiquement montrer que l'élément est sélectionné
            ((MapView) map.getDrawing()).setTempElement(new TemporaryTileView(item, map, shop, sideBarView));
        }
    }
}
