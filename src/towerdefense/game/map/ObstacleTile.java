package towerdefense.game.map;

import towerdefense.view.map.ObstacleTileView;

/**
 * Case conteannt un obstable sur laquelle il est donc impossible de construire ou pour un NPC de se déplacer
 * Cette case n'est pas un chemin
 */
public class ObstacleTile extends Tile {

    // ==================== Initilisation ====================
    public ObstacleTile(int x, int y, Map map, TileType type) {
        super(x, y, map);
        this.ID = type;
        isBlocked = true;
    }

    //==================== Interface Drawable ====================

    /**
     * Initilisation de la vue correpsondant à une case
     */
    @Override
    public void initDrawing() {
        tileView = new ObstacleTileView(map, this, ID);
    }
}
