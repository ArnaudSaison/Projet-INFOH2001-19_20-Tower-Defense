package towerdefense.game.map;

import towerdefense.game.Drawable;
import towerdefense.view.Printable;
import towerdefense.view.map.TileView;

/**
 * Une Tile représente une case de la carte
 */
public abstract class Tile implements Drawable {
    //==================== Attributs ====================
    protected TileType ID;
    private Position tilePosition; // stocke la position du coin supérieur gauche de la case
    protected Map map;
    protected TileView tileView;
    protected boolean isBlocked;

    //==================== Initilisation ====================

    /**
     * Consructeur commun à toutes les cases
     */
    public Tile(int x, int y, Map map) {
        this.map = map;
        tilePosition = new Position(x, y, map);
        isBlocked = false;
    }

    //==================== Getters et setters ====================

    /**
     * Récupérer la position correspondant au coin supérieur gauche de la case
     * Position toujors en mètres
     */
    public Position getPosition() {
        return tilePosition;
    }

    /**
     * Redéfinir la position de la case
     * Position toujors en mètres
     */
    public void setPosition(Position position) {
        this.tilePosition = position;
    }

    /**
     * Récupérer la position correspondant au centre de la case
     * Position toujors en mètres
     */
    public Position getCenterPosition() {
        double dx = map.getTileMetricWidth() / 2;
        double dy = dx;
        return tilePosition.getAdded(new Position(dx, dy, map));
    }

    /**
     * Récupération de l'état de la case :
     * true : bloqué = on ne peut rien construire dessus
     * false : non-bloqué = on peut construire dessus
     */
    public boolean getBlockedState() {
        return isBlocked;
    }

    /**
     * Déifnition de l'état de la case :
     * true : bloqué = on ne peut rien construire dessus
     * false : non-bloqué = on peut construire dessus
     */
    public void setBlockedState(boolean blocked) {
        this.isBlocked = blocked;
    }

    //==================== Interface Drawable ====================

    /**
     * Initilisation de la vue correpsondant à une case
     */
    @Override
    public abstract void initDrawing();

    /**
     * Mise à jour de la vue correpsondant à une case
     * Requiert d'avoir d'abord initialisé la vue avec initDrawing()
     */
    @Override
    public void updateDrawing() {
        tileView.update();
    }

    /**
     * Récupération de la vue correpsondant à une case
     * Requiert d'avoir d'abord initialisé la vue avec initDrawing()
     */
    @Override
    public Printable getDrawing() {
        return tileView;
    }

    //==================== Autres ====================
    public TileType getTileType() {
        return ID;
    }

    /**
     * Représentation de la case sous forme de texte selon le format :
     * "Tile" + coordonnées de la case dans la grille de la carte
     */
    @Override
    public String toString() {
        return super.toString() + " = Tile" + tilePosition.getTileCoords();
    }
}
