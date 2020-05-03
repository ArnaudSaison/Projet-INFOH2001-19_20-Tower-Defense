package towerdefense.view.map;

import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;

/**
 * GUI : Classe représentant une case vide (herbe)
 */
public class EmptyTileView extends TileView {

    // ==================== Initialisation ====================

    /**
     * Constructeur
     */
    public EmptyTileView(Map map, Tile tile) {
        super(map, tile); // Appel au constructeur de TileView
        getTileShape().getStyleClass().addAll("empty-tile-shape"); // Style de case attaché à sa représentation graphique

        initTexture("grass.png", getRandomRotation(0,3, 90), 1, 1);
        initHoverIndicator(true);
    }
}
