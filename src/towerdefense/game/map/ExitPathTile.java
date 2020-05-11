package towerdefense.game.map;

import towerdefense.view.map.ExitPathTileView;

import java.util.ArrayList;

/**
 * Case de chemin représentant plus spécifiquement une sortie
 */
public class ExitPathTile extends PathTile {

    // ==================== Initilisation ====================
    public ExitPathTile(int x, int y, Map map) {
        super(x, y, map);
        ID = TileType.EXIT_PATH;
    }

    //==================== Interface Drawable ====================

    /**
     * Initilisation de la vue correpsondant à une case
     */
    @Override
    public void initDrawing() {
        ArrayList<Connections> connections = getConnections(); // récupération des connections
        // récupération de la dernière connection ajoutée car ce sera toujours la direction de la sortie (voir PathFactory)
        Connections arrowConnection;

        if (!connections.isEmpty()) {
            arrowConnection = connections.get(connections.size() - 1);
        } else {
            arrowConnection = Connections.RIGHT;
        }
        tileView = new ExitPathTileView(map, this, arrowConnection);
    }
}
