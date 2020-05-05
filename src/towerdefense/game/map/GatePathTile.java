package towerdefense.game.map;

import towerdefense.view.GatePathTileView;

/**
 * Case de chemin représentant plus spécifiquement une entrée
 */
public class GatePathTile extends PathTile {

    // ==================== Initilisation ====================
    public GatePathTile(int x, int y, double tileMetricWidth) {
        super(x, y, tileMetricWidth);
    }

    //==================== Interface Drawable ====================

    /**
     * Initilisation de la vue correpsondant à une case
     */
    @Override
    public void initDrawing() {
        // Récupérer la connection permet d'afficher dans la bonne direction la flèche indiquant qu'il s'agit d'une entrée
        // récupération de la première connection ajoutée car ce sera toujours la direction de l'entrée (voir PathFactory)
        tileView = new GatePathTileView(map, this, getConnections().get(0));
    }
}
