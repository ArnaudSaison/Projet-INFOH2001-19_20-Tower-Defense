package towerdefense.view.goldmines;

import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.view.ElementView;

public class GoldMineView extends ElementView {
    // ==================== Attributs ====================
    // Références
    private GoldMine goldMine;
    private Map map;

    // ==================== Initilisation ====================

    public GoldMineView(GoldMine goldMine, Map map, String graphicsFileName, int proportion) {
        super(goldMine.getPosition(), map, graphicsFileName, proportion);

        // Références
        this.goldMine = goldMine;
        this.map = map;
    }
}
