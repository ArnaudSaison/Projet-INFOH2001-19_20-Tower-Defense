package towerdefense.game.map;

import java.util.ArrayList;

public class Path {
    private ArrayList<IntCoordinates> positions;

    /** La classe Path décrit un chemin qui peut être parcourru
     * */
    public Path(ArrayList<IntCoordinates> positions){
        this.positions = positions;
    }
}
