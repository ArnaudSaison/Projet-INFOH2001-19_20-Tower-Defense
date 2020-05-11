package towerdefense.game.map;

import towerdefense.view.map.GatePathTileView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Case de chemin représentant plus spécifiquement une entrée
 */
public class GatePathTile extends PathTile {
    // ==================== Attributs ====================
    private ArrayList<Path> paths;
    private Random random;

    // ==================== Initilisation ====================
    public GatePathTile(int x, int y, Map map) {
        super(x, y, map);
        ID = TileType.GATE_PATH;

        this.paths = new ArrayList<>();
        random = new Random();
    }

    /**
     * méthode pour attacher des chemins à une case d'entrée
     *
     * @param pathsToAttach chemins à attacher à la case d'entrée
     */
    public void attachPaths(ArrayList<Path> pathsToAttach) {
        paths.addAll(pathsToAttach);
    }

    /**
     * Réinitialise les chemins attachés à la case en vidant la liste
     */
    public void reinitialisePaths() {
        paths.clear();
    }

    /**
     * Sélectionne aléatoirement un chemin par ceux disponibles depuis cette case
     *
     * @return Path Chemin à partir de cette case choisi aléatoirement
     */
    public Path getRandomPath() {
        return paths.get(random.nextInt(paths.size()));
    }

    /**
     * Vider la liste des chemins calculés
     */
    public void clearPaths() {
        paths.clear();
    }

    //==================== Interface Drawable ====================

    /**
     * Initilisation de la vue correpsondant à une case
     */
    @Override
    public void initDrawing() {
        // Récupérer la connection permet d'afficher dans la bonne direction la flèche indiquant qu'il s'agit d'une entrée
        // récupération de la première connection ajoutée car ce sera toujours la direction de l'entrée (voir PathFactory)
        ArrayList<Connections> connections = getConnections();
        Connections arrowConnection;

        if (!connections.isEmpty()) {
            arrowConnection = connections.get(0);
        } else {
            arrowConnection = Connections.RIGHT;
        }

        tileView = new GatePathTileView(map, this, arrowConnection);
    }
}
