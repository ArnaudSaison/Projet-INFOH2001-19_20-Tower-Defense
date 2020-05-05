package towerdefense.game.map;

import towerdefense.view.PathTileView;

import java.util.ArrayList;

/**
 * Case représentant un chemin sur lequel les ennemis peuvent se déplacer
 */
public class PathTile extends Tile {
    //==================== Attributs ====================
    // Type particulier de classe permettant de stocker des variables très limitées, mais fixées et facilement compréhensibles
    // Note : il est nécessaire de rendre cette classe publique afin de pouvoir accéder à son contenu depusi d'autres classes
    // Cependant celle-ci n'est pas modifiable et ne peut donc pas être mal utilisée par ces autres classes
    public enum Connections {TOP, BOTTOM, LEFT, RIGHT}

    private ArrayList<Connections> connections;

    // ==================== Initilisation ====================

    /**
     * Constructeur de la classe
     */
    public PathTile(int x, int y, double tileMetricWidth) {
        super(x, y, tileMetricWidth);
        connections = new ArrayList<>();
    }

    // ==================== Gestion des connections entre cases ====================

    /**
     * Méthode permettant de rajouter une connection.
     * Une case ne peut pas contenir des connections redondantes
     * et n'ajoutera rien à sa liste de connections si une est détectée
     */
    public void addConnection(Connections connection) {
        if (!connections.contains(connection)) {
            connections.add(connection);
        }
    }

    /**
     * Méthode qui vide la liste des connections
     */
    public void reinitializeConnections() {
        connections.clear();
    }

    /**
     * Méthode permettant de vérifier si une case est connectée à une autre dans une certaine direction
     */
    public boolean getConnection(Connections connection) {
        return connections.contains(connection);
    }

    /**
     * Méthode qui renvoie une liste des
     */
    public ArrayList<Connections> getConnections() {
        return connections;
    }

    //==================== Interface Drawable ====================

    /**
     * Initilisation de la vue correpsondant à une case
     */
    @Override
    public void initDrawing() {
        tileView = new PathTileView(map, this);
    }
}
