package towerdefense.view.towers;

import towerdefense.game.map.Map;
import towerdefense.game.towers.Tower;
import towerdefense.view.ElementView;

public class TowerView extends ElementView {
    // ==================== Attributs ====================
    // Références
    private Tower tower;
    private Map map;

    // ==================== Initilisation ====================

    public TowerView(Tower tower, Map map, String graphicsFileName, int proportion) {
        super(tower.getPosition(), map, graphicsFileName, proportion);

        // Références
        this.tower = tower;
        this.map = map;

//        setOnMouseClicked();
    }
}
