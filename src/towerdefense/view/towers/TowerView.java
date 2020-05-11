package towerdefense.view.towers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import towerdefense.game.map.Map;
import towerdefense.game.model.Shop;
import towerdefense.game.towers.Tower;
import towerdefense.view.ElementView;
import towerdefense.view.upgradePrompt;

public class TowerView extends ElementView {
    // ==================== Attributs ====================
    // Références
    private Tower tower;
    private Map map;

    // ==================== Initilisation ====================

    public TowerView(Tower tower, Map map, String graphicsFileName, int proportion, Shop shop) {
        super(tower.getPosition(), map, graphicsFileName, proportion);

        // Références
        this.tower = tower;
        this.map = map;

        imageView.getStyleClass().add("hand-cursor");

        setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                this.getChildren().add(new upgradePrompt(tower.getID(), tower, shop, this));
            }
        });
    }
}
