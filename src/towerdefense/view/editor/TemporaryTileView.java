package towerdefense.view.editor;

import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.Shop;
import towerdefense.view.ElementView;
import towerdefense.view.TemporaryItem;
import towerdefense.view.map.MapView;
import towerdefense.view.shop.ShopView;

public class TemporaryTileView extends ElementView implements TemporaryItem {
    // ==================== Attributs ====================
    // Références
    private Shop.ShopCases itemType;
    private Position tilePosition;
    private Shop shop;
    private Map map;
    private Position correctionPos;

    // ==================== Initilisation ====================

    public TemporaryTileView(Map map, Shop shop, ShopView shopView) {
        super(new Position(0, 0, map), map, Shop.getIconPath(itemType), Shop.getGraphicsProportion(itemType));
        this.itemType = itemType;
        this.shop = shop;
        this.map = map;
        double correction = Shop.getGraphicsProportion(itemType) * map.getTileMetricWidth() / 2.0;
        this.correctionPos = new Position(correction, correction, map);
        setPosition(new Position(0, 0, map));

        // Réglage graphique
        this.setOpacity(0.5);

        // listener qui appelle shop quand on clique
        this.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                boolean built = shop.buyPlaceable(itemType, tilePosition, map); // On essaye d'acheter l'item
                if (built) { // S'il a pu être placé, il faut supprimer la représentation temporaire
                    ((MapView) map.getDrawing()).removeTempElement(); // Cast permis puisque le Drawing d'une map sera toujours une MapView
                    shopView.deselectItems();
                }
            }
        });
    }

    // ==================== Getters / setters ====================

    /**
     * Permet de changer la positon associée à l'élément graphique
     */
    public void setPosition(Position pos) {
        tilePosition = pos; // position de la case qui sert de référence
        this.position = pos.getAdded(correctionPos); //position du centre
    }
}