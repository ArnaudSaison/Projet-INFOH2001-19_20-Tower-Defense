package towerdefense.view;

import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;

/**
 * GUI : Classe représentant une case de chemin
 */
public class PathTileView extends TileView {
    // ==================== Attributs ====================


    // ==================== Initialisation ====================

    /**
     * Constructeur de la classe représentant une case de chemin
     */
    public PathTileView(Map map, Tile tile) {
        super(map, tile); // Appel au constructeur de TileView
        this.getTileShape().getStyleClass().addAll("path-tile", "cannot-be-built-on");
    }
}
