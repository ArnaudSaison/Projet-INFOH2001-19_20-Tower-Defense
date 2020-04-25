package towerdefense.game.map;

import java.util.ArrayList;

public class Path {
    private ArrayList<IntCoordinates> positions;

    /**
     * La classe Path décrit un chemin qui peut être parcourru
     */
    public Path(ArrayList<IntCoordinates> positions) {
        this.positions = positions;
    }

    public ArrayList<IntCoordinates> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<IntCoordinates> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        String res = "Path = ";
        for (IntCoordinates pos : positions) {
            res += pos.toString() + " ";
        }
        return res;
    }
}
