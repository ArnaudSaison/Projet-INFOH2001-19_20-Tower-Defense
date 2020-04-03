package Model;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class NPC {
    protected Point2D.Double position;
    protected Path path;
    private int loot;
    //private ArrayList<Weapon> weapons; (optionnel)

    public NPC (Point2D.Double position, Path path){
        this.position = position;
        this.path = path;
    }
}
