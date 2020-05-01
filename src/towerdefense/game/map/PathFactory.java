package towerdefense.game.map;

import java.util.ArrayList;

public class PathFactory {
    private final Map map;
    private ArrayList<Path> paths;

    public PathFactory(Map map) {
        this.map = map;
    }

    public ArrayList<Path> getAllPaths(Tile gate) {
        // Initilisation de la liste qui va contenir tous les chemins
        paths = new ArrayList<>();

        // Initialisation de la récurrance avec le premier élément à analyser
        ArrayList<IntCoordinates> positions = new ArrayList<>();
        ArrayList<IntCoordinates> visited = new ArrayList<>();

        positions.add(gate.getPosition().getTileCoords());
        visited.add(gate.getPosition().getTileCoords());

        PathTile.Connections fromSide;
        fromSide = getMapSide(gate.getPosition().getTileCoords());

        ((PathTile) gate).addConnection(fromSide);

        // Initilisation de la recherche de chemin
        searchForPaths(positions, visited, positions.get(0), fromSide);

        return paths;
    }

    private void searchForPaths(ArrayList<IntCoordinates> positions, ArrayList<IntCoordinates> visited, IntCoordinates currentCoords, PathTile.Connections fromSide) {
        ArrayList<Tile> adjacentTiles = getAdjacentTiles(currentCoords); // toutes les cases adjacentes à la case actuellement étudiée (qu'elles existent ou non)

        for (Tile probedTile : adjacentTiles) {

            if (probedTile instanceof PathTile && !(probedTile instanceof GatePathTile)) { // Si la case existe et n'est pas une entrée
                IntCoordinates probedTileCoords = probedTile.getPosition().getTileCoords(); // Récupération des coordonnées de la case

                if (!visited.contains(probedTileCoords)) { // Si la position n'appartient pas encore au chemin
                    PathTile.Connections toSide = getToSideDir(currentCoords, probedTileCoords); // côté par lequel on va arriver dans cette case

                    // Modification des cases pour qu'elle puissent graphiquement former les chemins
                    PathTile currentTile = (PathTile)map.getTile(currentCoords.getX(), currentCoords.getY());
                    currentTile.addConnection(getOppositeDir(toSide));
                    currentTile.addConnection(getOppositeDir(fromSide));

                    // nouvelle liste de position pour ne pas influencer les autres chemins possibles
                    ArrayList<IntCoordinates> resPositions = new ArrayList<>(positions);
                    ArrayList<IntCoordinates> resVisited = new ArrayList<>(visited);

                    resVisited.add(probedTileCoords); // on marque la case comme visitée
                    if (toSide != fromSide) { // Si changement de direction
                        resPositions.add(currentCoords); // Ajouter la case actuelle à la liste des positions
                    }

                    if (probedTile instanceof ExitPathTile) { // Si la case est une sortie
                        resPositions.add(probedTileCoords); // Ajouter la case de sortie
                        paths.add(new Path(resPositions)); // Comme il s'agit d'une sortie, on crée un chemin (condition de sortie de récurrence)

                        // Ajout de la connection vers le bord de la map
                        ((PathTile) probedTile).addConnection(toSide);
                        ((PathTile) probedTile).addConnection(getMapSide(probedTileCoords));

                    } else { // Sinon, la case était valide, mais pas une sortie, on va donc voir autour de celle-ci (récurrence)
                        searchForPaths(resPositions, resVisited, probedTileCoords, getOppositeDir(toSide)); //
                    }
                }
            } else {
                deadEnd();
            }
        }
    }

    private void deadEnd() {
    }

    private PathTile.Connections getMapSide(IntCoordinates tile) {
        // recherche du bord de la carte
        ArrayList<Tile> adjacentTiles = getAdjacentTiles(tile);
        int index = adjacentTiles.indexOf(null); // la position dans la liste permet de déduire le côté
        PathTile.Connections fromSide;
        switch (index) {
            case 0:
                fromSide = PathTile.Connections.TOP;
                break;
            case 1:
                fromSide = PathTile.Connections.RIGHT;
                break;
            case 2:
                fromSide = PathTile.Connections.BOTTOM;
                break;
            case 3:
                fromSide = PathTile.Connections.LEFT;
                break;
            default:
                fromSide = PathTile.Connections.TOP;
        }

        return fromSide;
    }

    private ArrayList<Tile> getAdjacentTiles(IntCoordinates tileCoords) {
        ArrayList<Tile> adjacentTiles = new ArrayList<>();
        int x = tileCoords.getX();
        int y = tileCoords.getY();
        adjacentTiles.add(map.getTile(x, y - 1));
        adjacentTiles.add(map.getTile(x + 1, y));
        adjacentTiles.add(map.getTile(x, y + 1));
        adjacentTiles.add(map.getTile(x - 1, y));

        return adjacentTiles;
    }

    private PathTile.Connections getToSideDir(IntCoordinates from, IntCoordinates to) {
        PathTile.Connections res = PathTile.Connections.TOP;
        if (from.getX() == to.getX() && from.getY() < to.getY()) {
            res = PathTile.Connections.TOP;
        } else if (from.getX() == to.getX() && from.getY() > to.getY()) {
            res = PathTile.Connections.BOTTOM;
        } else if (from.getX() < to.getX() && from.getY() == to.getY()) {
            res = PathTile.Connections.LEFT;
        } else if (from.getX() > to.getX() && from.getY() == to.getY()) {
            res = PathTile.Connections.RIGHT;
        }
        return res;
    }

    private PathTile.Connections getOppositeDir(PathTile.Connections dir) {
        PathTile.Connections res;
        switch (dir) {
            case TOP:
                res = PathTile.Connections.BOTTOM;
                break;
            case BOTTOM:
                res = PathTile.Connections.TOP;
                break;
            case LEFT:
                res = PathTile.Connections.RIGHT;
                break;
            case RIGHT:
                res = PathTile.Connections.LEFT;
                break;
            default:
                res = PathTile.Connections.BOTTOM;
        }
        return res;
    }
}
