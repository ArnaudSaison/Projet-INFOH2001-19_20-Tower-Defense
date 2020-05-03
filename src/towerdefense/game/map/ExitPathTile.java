package towerdefense.game.map;

import towerdefense.view.map.ExitPathTileView;

import java.util.ArrayList;

/**
 * Case de chemin représentant plus spécifiquement une sortie
 * */
public class ExitPathTile extends PathTile {

    // ==================== Initilisation ====================
    public ExitPathTile(int x, int y, double tileMetricWidth){
        super(x, y, tileMetricWidth);
    }

    //==================== Interface Drawable ====================
    /** Initilisation de la vue correpsondant à une case
     * */
    @Override
    public void initDrawing() {
        ArrayList<Connections> connections = getConnections(); // récupération des connections
        // récupération de la dernière connection ajoutée car ce sera toujours la direction de la sortie (voir PathFactory)
        tileView = new ExitPathTileView(map, this, connections.get(connections.size() - 1));
    }
}
