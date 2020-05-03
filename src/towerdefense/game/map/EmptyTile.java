package towerdefense.game.map;

import towerdefense.view.map.EmptyTileView;

/**
 * Case vide de la carte (herbe) où les monstres ne peuvent pas se déplacer, mais où on peut construrie
 */
public class EmptyTile extends Tile {

    // ==================== Initilisation ====================
    public EmptyTile(int x, int y, double tileMetricWidth) {
        super(x, y, tileMetricWidth);
        isBlocked = false;
    }

    //==================== Interface Drawable ====================

    /**
     * Initilisation de la vue correpsondant à une case
     */
    @Override
    public void initDrawing() {
        tileView = new EmptyTileView(map, this);
    }
}
