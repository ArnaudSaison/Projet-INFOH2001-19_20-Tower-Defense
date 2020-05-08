package towerdefense.view.shop;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import towerdefense.game.map.Map;
import towerdefense.game.model.Shop;
import towerdefense.view.map.MapView;

public class ItemClicked implements EventHandler<MouseEvent> {
    private Map map;
    private Shop.ShopCases item;
    private Node buyableItem;
    private Shop shop;
    private ShopView shopView;

    public ItemClicked(Map map, Shop.ShopCases item, Node buyableItem, Shop shop, ShopView shopView) {
        this.map = map;
        this.item = item;
        this.buyableItem = buyableItem;
        this.shop = shop;
        this.shopView = shopView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (shopView.getSelectedState(buyableItem)) {
            shopView.deselectItem(buyableItem);
            ((MapView) map.getDrawing()).removeTempElement();
        } else {
            shopView.setSelectedItem(buyableItem); //graphiquement montrer que l'élément est sélectionné
            ((MapView) map.getDrawing()).setTempElement(new TemporaryItemView(item, map, shop));
        }
    }
}
