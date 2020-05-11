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

    private GoldBubbleView goldBubbleView;

    // ==================== Initilisation ====================

    public GoldMineView(GoldMine goldMine, Map map, String graphicsFileName, int proportion, Shop shop) {
        super(goldMine.getPosition(), map, graphicsFileName, proportion);

        // Références
        this.goldMine = goldMine;
        this.map = map;

        imageView.getStyleClass().add("hand-cursor");

        // bulle sur laquelle le joueur peut cliquer pour récupérer l'or
        goldBubbleView = new GoldBubbleView(goldMine, map.getTileMetricWidth() * map.getSettingsPixelsPerMeter() / 2.0);
        goldBubbleView.setVisible(false);
        this.getChildren().add(goldBubbleView);

        // Fenêtre d'upgrade
        setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && !goldBubbleView.isVisible()) {
                this.getChildren().add(new upgradePrompt(goldMine.getID(), goldMine, shop, this));
            }
        });
    }

    @Override
    public void update() {
        super.update();

        // Le joueur ne peut récupérer l'or de la mine que si elle est remplie à 20%
        if (goldMine.getGoldStorage() >= goldMine.getMaxGoldStorage() * 0.50) {
            goldBubbleView.setNotFull();
            goldBubbleView.setVisible(true);
        } else {
            goldBubbleView.setNotFull();
            goldBubbleView.setVisible(false);
        }

        if (goldMine.getGoldStorage() == goldMine.getMaxGoldStorage()) {
            goldBubbleView.setFull();
        }
    }
}
