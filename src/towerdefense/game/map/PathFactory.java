package towerdefense.game.map;

import java.util.ArrayList;

public class PathFactory {
    private final Map map;
    private ArrayList<Path> paths;

    private enum dir {TOP, BOTTOM, RIGHT, LEFT}

    public PathFactory(Map map){
        this.map = map;
    }

    public ArrayList<Path> getAllPaths(Tile gate){
        // Initilisation de la liste qui va contenir tous les chemins
        paths = new ArrayList<>();

        ArrayList<IntCoordinates> positions = new ArrayList<>();
        positions.add(gate.getPosition().getTileCoords());

        dir side = dir.TOP;

        // Initilisation de la recherche de chemin
        searchForPaths(positions, side);

        return paths;
    }

    private void searchForPaths(ArrayList<IntCoordinates> positions, dir fromSide){
        int lastIndex = positions.size() - 1; // coordonnées de la case qu'on étudie
        IntCoordinates currentPos = positions.get(lastIndex); // récupère le dernier élément
        ArrayList<Tile> adjacentTiles = getAdjacentTiles(currentPos);

        if (positions.size() > 1){
            int fromSide = ;

        } else(){

        }

        for (int i = 0; i < adjacentTiles.size(); i++){
            Tile tile = adjacentTiles.get(i);
            dir toSide = getSideDir();

            if (tile != null && toSide != fromSide) {
                if (tile instanceof ExitPathTile){
                    paths.add(new Path(positions));

                } else if (tile instanceof PathTile && !(tile instanceof GatePathTile)) {
                    // On créée une nouvelle liste de positons afin de permettre la séparation en pluieurs chemins
                    // Ainsi, on n'ajoutera pas de position dans une liste qui n'appartient pas au chemin concerné
                    ArrayList<IntCoordinates> resPos = new ArrayList<>(positions);
                }
            }
        }
    }

    private ArrayList<Tile> getAdjacentTiles(IntCoordinates tileCoords){
        ArrayList<Tile> adjacentTiles = new ArrayList<>();
        int x = tileCoords.getX();
        int y = tileCoords.getY();
        adjacentTiles.add(map.getTile(x, y+1));
        adjacentTiles.add(map.getTile(x+1, y));
        adjacentTiles.add(map.getTile(x, y-1));
        adjacentTiles.add(map.getTile(x-1, y));

        return adjacentTiles;
    }

    private dir getSideDir(IntCoordinates from, IntCoordinates to){
        if (){
            dir res = dir.RIGHT;
        }
        return res;
    }
}
