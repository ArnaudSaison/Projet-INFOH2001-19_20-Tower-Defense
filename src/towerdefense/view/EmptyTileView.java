package towerdefense.view;

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
        this.getTileShape().getStyleClass().addAll("empty-tile", "can-be-built-on"); // Style de case attaché à sa représentation graphique
    }

    /**
     * Méthode qui renvoit une rotation aléatoire multiple de 90 degrés
     */
    public void getRandomRotation() {

    }
}
