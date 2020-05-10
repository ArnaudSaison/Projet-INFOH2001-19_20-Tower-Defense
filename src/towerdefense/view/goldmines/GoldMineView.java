package towerdefense.view.goldmines;

import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.model.Shop;
import towerdefense.view.ElementView;
import towerdefense.view.upgradePrompt;

public class GoldMineView extends ElementView {
    // ==================== Attributs ====================
    // Références
    private GoldMine goldMine;
    private Map map;

    // ==================== Initilisation ====================

    public GoldMineView(GoldMine goldMine, Map map, String graphicsFileName, int proportion, Shop shop) {
        super(goldMine.getPosition(), map, graphicsFileName, proportion);

        // Références
        this.goldMine = goldMine;
        this.map = map;

        imageView.getStyleClass().add("hand-cursor");

        setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                this.getChildren().add(new upgradePrompt(goldMine.getID(), goldMine, shop, this));
            }
        });
    }
}
