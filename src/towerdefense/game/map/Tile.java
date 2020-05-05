package towerdefense.game.map;

import javafx.scene.Node;
import towerdefense.game.Drawable;
import towerdefense.view.Printable;
import towerdefense.view.TileView;

/**
 * Une Tile représente une case de la carte
 */
public abstract class Tile implements Drawable {
    //==================== Attributs ====================
    private Position tilePosition; // stocke la position du coin supérieur gauche de la case
    protected Map map;
    protected TileView tileView;
    protected boolean isBlocked;

    //==================== Initilisation ====================

    /**
     * Consructeur commun à toutes les cases
     */
    public Tile(int x, int y, double tileMetricWidth) {
        tilePosition = new Position(x, y, tileMetricWidth);
        isBlocked = false;
    }

    /**
     * Cette méthode permet de créer une case et de l'attacher ensuite à une carte
     */
    public void attachMap(Map map) {
        this.map = map;
        tilePosition.attachMap(map);
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
    public abstract void initDrawing();

    /**
     * Mise à jour de la vue correpsondant à une case
     * Requiert d'avoir d'abord initialisé la vue avec initDrawing()
     */
    public void updateDrawing() {
        tileView.update();
    }

    /**
     * Récupération de la vue correpsondant à une case
     * Requiert d'avoir d'abord initialisé la vue avec initDrawing()
     */
    public Printable getDrawing() {
        return tileView;
    }

    /**
     * Retirer la représentation graphique de l'élément de la vue
     * Cette méthode ne sert pas à faire disparaître la représentation en question,
     * mais bien à supprimer toute référence de cette raprésentation et ainsi pouvoir supprimer this
     * */
    public void removeDrawing() {
        // TODO: ajouter possibilité de supprimer les tiles, les remplacer, ...
    }

    //==================== Autres ====================

    /**
     * Représentation de la case sous forme de texte selon le format :
     * "Tile" + coordonnées de la case dans la grille de la carte
     */
    @Override
    public String toString() {
        return "Tile" + tilePosition.getTileCoords();
    }
}
